package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Ejemplar;
import edu.itm.proyecto2026.repositories.EjemplarRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjemplarServices implements EjemplarServiceInterface {

    private final EjemplarRepositoryInterface ejemplarRepository;

    public EjemplarServices(EjemplarRepositoryInterface ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplarRepository.getEjemplares();
    }

    public Ejemplar getEjemplar(Integer id) {
        return ejemplarRepository.getEjemplar(id);
    }

    public Ejemplar insertarEjemplar(Ejemplar ejemplar) {
        return ejemplarRepository.insertarEjemplar(ejemplar);
    }

    public Ejemplar actualizarEjemplar(Ejemplar ejemplar) {
        return ejemplarRepository.actualizarEjemplar(ejemplar);
    }

    public boolean eliminarEjemplar(Integer id) {
        return ejemplarRepository.eliminarEjemplar(id);
    }
}
