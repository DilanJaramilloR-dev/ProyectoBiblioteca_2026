package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Sancion;
import edu.itm.proyecto2026.repositories.SancionRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SancionServices implements SancionServiceInterface {

    private final SancionRepositoryInterface sancionRepository;

    public SancionServices(SancionRepositoryInterface sancionRepository) {
        this.sancionRepository = sancionRepository;
    }

    public List<Sancion> getSanciones() {
        return sancionRepository.getSanciones();
    }

    public Sancion getSancion(Integer id) {
        return sancionRepository.getSancion(id);
    }

    public Sancion insertarSancion(Sancion sancion) {
        return sancionRepository.insertarSancion(sancion);
    }

    public Sancion actualizarSancion(Sancion sancion) {
        return sancionRepository.actualizarSancion(sancion);
    }
}