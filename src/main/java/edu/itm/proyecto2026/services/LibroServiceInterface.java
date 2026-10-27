package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Libro;

import java.util.List;

public interface LibroServiceInterface {
    List<Libro> getLibros();
    Libro getLibro(Integer id);
    Libro insertarLibro(Libro libro);
    Libro actualizarLibro(Libro libro);
    boolean eliminarLibro(Integer id);
}