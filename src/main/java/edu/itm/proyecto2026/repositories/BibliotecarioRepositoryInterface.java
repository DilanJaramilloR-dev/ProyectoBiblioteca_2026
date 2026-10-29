package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Bibliotecario;

import java.util.List;

public interface BibliotecarioRepositoryInterface {
    List<Bibliotecario> getBibliotecarios();
    Bibliotecario getBibliotecario(Integer id);
    Bibliotecario insertarBibliotecario(Bibliotecario bibliotecario);
    Bibliotecario actualizarBibliotecario(Bibliotecario bibliotecario);
    boolean eliminarBibliotecario(Integer id);
}