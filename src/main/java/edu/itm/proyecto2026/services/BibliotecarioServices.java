package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Bibliotecario;
import edu.itm.proyecto2026.repositories.BibliotecarioRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliotecarioServices implements BibliotecarioServiceInterface {

    private final BibliotecarioRepositoryInterface bibliotecarioRepository;

    public BibliotecarioServices(BibliotecarioRepositoryInterface bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarioRepository.getBibliotecarios();
    }

    public Bibliotecario getBibliotecario(Integer id) {
        return bibliotecarioRepository.getBibliotecario(id);
    }

    public Bibliotecario insertarBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.insertarBibliotecario(bibliotecario);
    }

    public Bibliotecario actualizarBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.actualizarBibliotecario(bibliotecario);
    }

    public boolean eliminarBibliotecario(Integer id) {
        return bibliotecarioRepository.eliminarBibliotecario(id);
    }
}
