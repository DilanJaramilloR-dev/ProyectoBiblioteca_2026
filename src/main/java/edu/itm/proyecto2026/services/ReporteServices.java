package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServices implements ReporteServiceInterface {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;

    public ReporteServices(PrestamoRepository prestamoRepository, LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<ReportePrestamo> getPrestamosActivos() {
        LocalDate hoy = LocalDate.now();
        return prestamoRepository.buscarActivos(hoy).stream()
                .map(prestamo -> aReportePrestamo(prestamo, hoy))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReportePrestamo> getPrestamosVencidos() {
        LocalDate hoy = LocalDate.now();
        return prestamoRepository.buscarVencidos(hoy).stream()
                .map(prestamo -> aReportePrestamo(prestamo, hoy))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReporteInventario> getInventario() {
        return libroRepository.findAllByOrderByTituloLibroAsc().stream()
                .map(this::aReporteInventario)
                .collect(Collectors.toList());
    }

    private ReportePrestamo aReportePrestamo(Prestamo prestamo, LocalDate hoy) {
        long retraso = Math.max(ChronoUnit.DAYS.between(prestamo.getFechaDevolucionPrevista(), hoy), 0);
        Persona usuario = prestamo.getUsuario().getPersona();
        Persona bibliotecario = prestamo.getBibliotecario().getPersona();
        return ReportePrestamo.builder()
                .idPrestamo(prestamo.getIdPrestamo().longValue())
                .codigoInventario(prestamo.getEjemplar().getCodigoInventario())
                .tituloLibro(prestamo.getEjemplar().getLibro().getTituloLibro())
                .nombreUsuario(usuario.getNombrePersona() + " " + usuario.getApellidoPersona())
                .nombreBibliotecario(bibliotecario.getNombrePersona() + " " + bibliotecario.getApellidoPersona())
                .estadoPrestamo(prestamo.getEstadoPrestamo())
                .fechaPrestamo(prestamo.getFechaPrestamo())
                .fechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista())
                .diasRetraso((int) retraso)
                .build();
    }

    private ReporteInventario aReporteInventario(Libro libro) {
        int total = libro.getEjemplares().size();
        int disponibles = (int) libro.getEjemplares().stream()
                .filter(ejemplar -> "si".equalsIgnoreCase(ejemplar.getDisponibilidadEjemplar()))
                .count();
        return ReporteInventario.builder()
                .idLibro(libro.getIdLibro().longValue())
                .tituloLibro(libro.getTituloLibro())
                .isbnLibro(libro.getIsbnLibro())
                .nombreCategoria(libro.getCategoria().getNombreCategoria())
                .totalEjemplares(total)
                .ejemplaresDisponibles(disponibles)
                .ejemplaresNoDisponibles(total - disponibles)
                .build();
    }
}
