package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Categoria;

import java.util.List;

public interface CategoriaRepositoryInterface {
    List<Categoria> getCategorias();
    Categoria getCategoria(Integer id);
    Categoria insertarCategoria(Categoria categoria);
    Categoria actualizarCategoria(Categoria categoria);
    boolean eliminarCategoria(Integer id);
}
