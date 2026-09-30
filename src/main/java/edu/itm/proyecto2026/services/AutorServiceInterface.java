package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Autor;

import java.util.List;

public interface AutorServiceInterface {
    List<Autor> getAutor();
    Autor insertarAutor(Autor autor);
    Autor actualizarAutor(Autor autor);
    Autor getAutor(Integer id);
    boolean eliminarAutor(Integer id);
}
