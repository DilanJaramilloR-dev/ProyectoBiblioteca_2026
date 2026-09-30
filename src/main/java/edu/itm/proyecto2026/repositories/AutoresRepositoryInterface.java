package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;

import java.util.List;

public interface AutoresRepositoryInterface {
    List<Autor> getAutor();
    Autor getAutor(Integer id);
    Autor insertarAutor(Autor autor);
    Autor actualizarAutor(Autor autor);
    boolean eliminarAutor(Integer id);
}
