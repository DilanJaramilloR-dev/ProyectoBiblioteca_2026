package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Bibliotecario;
import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaEntities.Sancion;
import edu.itm.proyecto2026.jpaEntities.Usuario;
import edu.itm.proyecto2026.jpaRepositories.BibliotecarioRepository;
import edu.itm.proyecto2026.jpaRepositories.EjemplarRepository;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
import edu.itm.proyecto2026.jpaRepositories.UsuarioRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoServices implements PrestamoServiceInterface {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final UsuarioRepository usuarioRepository;
    private final BibliotecarioRepository bibliotecarioRepository;

    public PrestamoServices(PrestamoRepository prestamoRepository,
                            EjemplarRepository ejemplarRepository,
                            UsuarioRepository usuarioRepository,
                            BibliotecarioRepository bibliotecarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.usuarioRepository = usuarioRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<Prestamo> getPrestamos() {
        return prestamoRepository.findAll();
    }

    public Prestamo getPrestamo(Integer id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Prestamo insertarPrestamo(Prestamo prestamo) {
        try {
            Ejemplar ejemplar = ejemplarRepository
                    .findById(prestamo.getEjemplar().getIdEjemplar()).orElse(null);
            Usuario usuario = usuarioRepository
                    .findById(prestamo.getUsuario().getIdUsuario()).orElse(null);
            Bibliotecario bibliotecario = bibliotecarioRepository
                    .findById(prestamo.getBibliotecario().getIdBibliotecario()).orElse(null);
            if (ejemplar == null || usuario == null || bibliotecario == null) {
                return null;
            }
            if (!"si".equalsIgnoreCase(ejemplar.getDisponibilidadEjemplar())) {
                return null;
            }

            Prestamo nuevo = Prestamo.builder()
                    .ejemplar(ejemplar)
                    .usuario(usuario)
                    .bibliotecario(bibliotecario)
                    .estadoPrestamo("activo")
                    .fechaPrestamo(prestamo.getFechaPrestamo())
                    .fechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista())
                    .build();
            Prestamo guardado = prestamoRepository.save(nuevo);

            ejemplar.setDisponibilidadEjemplar("no");
            ejemplarRepository.save(ejemplar);

            return guardado;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    @Transactional
    public Prestamo devolverPrestamo(Integer id) {
        try {
            Prestamo prestamo = prestamoRepository.findById(id).orElse(null);
            if (prestamo == null) {
                return null;
            }

            boolean yaDevuelto = "devuelto".equalsIgnoreCase(prestamo.getEstadoPrestamo());
            LocalDate hoy = LocalDate.now();

            prestamo.setEstadoPrestamo("devuelto");
            prestamo.setFechaDevolucionReal(hoy);

            Ejemplar ejemplar = prestamo.getEjemplar();
            ejemplar.setDisponibilidadEjemplar("si");
            ejemplarRepository.save(ejemplar);

            if (!yaDevuelto && hoy.isAfter(prestamo.getFechaDevolucionPrevista())) {
                Sancion sancion = Sancion.builder()
                        .prestamo(prestamo)
                        .tipoSancion("multa")
                        .motivoSancion("Devolución fuera del plazo acordado")
                        .fechaInicio(hoy)
                        .estadoSancion("activa")
                        .build();
                if (prestamo.getSanciones() == null) {
                    prestamo.setSanciones(new ArrayList<>());
                }
                prestamo.getSanciones().add(sancion);
            }

            return prestamoRepository.save(prestamo);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    public Prestamo actualizarPrestamo(Prestamo prestamo) {
        try {
            Prestamo existente = prestamoRepository.findById(prestamo.getIdPrestamo()).orElse(null);
            if (existente == null) {
                return null;
            }
            existente.setEstadoPrestamo(prestamo.getEstadoPrestamo());
            existente.setFechaDevolucionReal(prestamo.getFechaDevolucionReal());
            return prestamoRepository.save(existente);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarPrestamo(Integer id) {
        try {
            if (!prestamoRepository.existsById(id)) {
                return false;
            }
            prestamoRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
