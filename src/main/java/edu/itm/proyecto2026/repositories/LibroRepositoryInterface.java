package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Libro;

import java.util.List;

public interface LibroRepositoryInterface {
    List<Libro> getLibros();
    Libro getLibro(Integer id);
    Libro insertarLibro(Libro libro);
    Libro actualizarLibro(Libro libro);
    boolean eliminarLibro(Integer id);
}