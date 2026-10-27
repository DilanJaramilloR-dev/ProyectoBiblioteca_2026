package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Categoria;

import java.util.List;

public interface CategoriaServiceInterface {
    List<Categoria> getCategorias();
    Categoria getCategoria(Integer id);
    Categoria insertarCategoria(Categoria categoria);
    Categoria actualizarCategoria(Categoria categoria);
    boolean eliminarCategoria(Integer id);
}
