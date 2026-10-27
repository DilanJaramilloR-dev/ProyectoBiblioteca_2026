package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Libro;
import edu.itm.proyecto2026.repositories.LibroRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServices implements LibroServiceInterface {

    private final LibroRepositoryInterface libroRepository;

    public LibroServices(LibroRepositoryInterface libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> getLibros() {
        return libroRepository.getLibros();
    }

    public Libro getLibro(Integer id) {
        return libroRepository.getLibro(id);
    }

    public Libro insertarLibro(Libro libro) {
        return libroRepository.insertarLibro(libro);
    }

    public Libro actualizarLibro(Libro libro) {
        return libroRepository.actualizarLibro(libro);
    }

    public boolean eliminarLibro(Integer id) {
        return libroRepository.eliminarLibro(id);
    }
}