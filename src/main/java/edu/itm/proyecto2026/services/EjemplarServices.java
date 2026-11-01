package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.EjemplarRepository;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjemplarServices implements EjemplarServiceInterface {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarServices(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplarRepository.findAll();
    }

    public Ejemplar getEjemplar(Integer id) {
        return ejemplarRepository.findById(id).orElse(null);
    }

    public Ejemplar insertarEjemplar(Ejemplar ejemplar) {
        try {
            Libro libro = libroRepository.findById(ejemplar.getLibro().getIdLibro()).orElse(null);
            if (libro == null) {
                return null;
            }
            Ejemplar nuevo = Ejemplar.builder()
                    .libro(libro)
                    .codigoInventario(ejemplar.getCodigoInventario())
                    .fechaAdquisicion(ejemplar.getFechaAdquisicion())
                    .estadoEjemplar(ejemplar.getEstadoEjemplar())
                    .ubicacionEjemplar(ejemplar.getUbicacionEjemplar())
                    .disponibilidadEjemplar("si")
                    .build();
            return ejemplarRepository.save(nuevo);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Ejemplar actualizarEjemplar(Ejemplar ejemplar) {
        try {
            Ejemplar existente = ejemplarRepository.findById(ejemplar.getIdEjemplar()).orElse(null);
            if (existente == null) {
                return null;
            }
            Libro libro = libroRepository.findById(ejemplar.getLibro().getIdLibro()).orElse(null);
            if (libro == null) {
                return null;
            }
            existente.setLibro(libro);
            existente.setCodigoInventario(ejemplar.getCodigoInventario());
            existente.setFechaAdquisicion(ejemplar.getFechaAdquisicion());
            existente.setEstadoEjemplar(ejemplar.getEstadoEjemplar());
            existente.setUbicacionEjemplar(ejemplar.getUbicacionEjemplar());
            return ejemplarRepository.save(existente);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarEjemplar(Integer id) {
        try {
            if (!ejemplarRepository.existsById(id)) {
                return false;
            }
            ejemplarRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
