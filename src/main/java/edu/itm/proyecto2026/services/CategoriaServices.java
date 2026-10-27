package edu.itm.proyecto2026.services;


import edu.itm.proyecto2026.identities.Categoria;
import edu.itm.proyecto2026.repositories.CategoriaRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServices implements CategoriaServiceInterface {

    private final CategoriaRepositoryInterface categoriaRepository;

    public CategoriaServices(CategoriaRepositoryInterface categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepository.getCategorias();
    }

    public Categoria getCategoria(Integer id) {
        return categoriaRepository.getCategoria(id);
    }

    public Categoria insertarCategoria(Categoria categoria) {
        return categoriaRepository.insertarCategoria(categoria);
    }

    public Categoria actualizarCategoria(Categoria categoria) {
        return categoriaRepository.actualizarCategoria(categoria);
    }

    public boolean eliminarCategoria(Integer id) {
        return categoriaRepository.eliminarCategoria(id);
    }
}