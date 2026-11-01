package edu.itm.proyecto2026.services;


import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaRepositories.CategoriaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServices implements CategoriaServiceInterface {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServices(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoria(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria insertarCategoria(Categoria categoria) {
        try {
            categoria.setIdCategoria(null);
            if (categoria.getEstadoCategoria() == null) {
                categoria.setEstadoCategoria("activa");
            }
            return categoriaRepository.save(categoria);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Categoria actualizarCategoria(Categoria categoria) {
        try {
            if (!categoriaRepository.existsById(categoria.getIdCategoria())) {
                return null;
            }
            return categoriaRepository.save(categoria);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarCategoria(Integer id) {
        try {
            if (!categoriaRepository.existsById(id)) {
                return false;
            }
            categoriaRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}