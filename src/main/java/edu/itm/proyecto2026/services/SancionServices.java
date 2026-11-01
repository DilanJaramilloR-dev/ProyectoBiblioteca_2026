package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaEntities.Sancion;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
import edu.itm.proyecto2026.jpaRepositories.SancionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SancionServices implements SancionServiceInterface {

    private final SancionRepository sancionRepository;
    private final PrestamoRepository prestamoRepository;

    public SancionServices(SancionRepository sancionRepository, PrestamoRepository prestamoRepository) {
        this.sancionRepository = sancionRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<Sancion> getSanciones() {
        return sancionRepository.findAll();
    }

    public Sancion getSancion(Integer id) {
        return sancionRepository.findById(id).orElse(null);
    }

    public Sancion insertarSancion(Sancion sancion) {
        try {
            Prestamo prestamo = prestamoRepository
                    .findById(sancion.getPrestamo().getIdPrestamo()).orElse(null);
            if (prestamo == null) {
                return null;
            }
            Sancion nueva = Sancion.builder()
                    .prestamo(prestamo)
                    .tipoSancion(sancion.getTipoSancion())
                    .motivoSancion(sancion.getMotivoSancion())
                    .fechaInicio(sancion.getFechaInicio() != null ? sancion.getFechaInicio() : LocalDate.now())
                    .fechaFin(sancion.getFechaFin())
                    .estadoSancion(sancion.getEstadoSancion() != null ? sancion.getEstadoSancion() : "activa")
                    .build();
            return sancionRepository.save(nueva);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Sancion actualizarSancion(Sancion sancion) {
        try {
            Sancion existente = sancionRepository.findById(sancion.getIdSancion()).orElse(null);
            if (existente == null) {
                return null;
            }
            existente.setTipoSancion(sancion.getTipoSancion());
            existente.setMotivoSancion(sancion.getMotivoSancion());
            existente.setFechaFin(sancion.getFechaFin());
            existente.setEstadoSancion(sancion.getEstadoSancion());
            return sancionRepository.save(existente);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}