package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.AutorRepository;
import edu.itm.proyecto2026.jpaRepositories.CategoriaRepository;
import edu.itm.proyecto2026.jpaRepositories.EditorialRepository;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroServices implements LibroServiceInterface {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final EditorialRepository editorialRepository;
    private final AutorRepository autorRepository;

    public LibroServices(LibroRepository libroRepository,
                         CategoriaRepository categoriaRepository,
                         EditorialRepository editorialRepository,
                         AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
        this.editorialRepository = editorialRepository;
        this.autorRepository = autorRepository;
    }

    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }

    public Libro getLibro(Integer id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro insertarLibro(Libro libro) {
        try {
            libro.setIdLibro(null);
            if (!resolverReferencias(libro)) {
                return null;
            }
            return libroRepository.save(libro);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Libro actualizarLibro(Libro libro) {
        try {
            if (!libroRepository.existsById(libro.getIdLibro())) {
                return null;
            }
            if (!resolverReferencias(libro)) {
                return null;
            }
            return libroRepository.save(libro);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarLibro(Integer id) {
        try {
            if (!libroRepository.existsById(id)) {
                return false;
            }
            libroRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private boolean resolverReferencias(Libro libro) {
        Categoria categoria = categoriaRepository
                .findById(libro.getCategoria().getIdCategoria()).orElse(null);
        Editorial editorial = editorialRepository
                .findById(libro.getEditorial().getIdEditorial()).orElse(null);
        if (categoria == null || editorial == null) {
            return false;
        }

        List<Autor> autores = new ArrayList<>();
        for (Autor referencia : libro.getAutores()) {
            if (referencia.getIdAutor() == null) {
                return false;
            }
            Autor autor = autorRepository.findById(referencia.getIdAutor()).orElse(null);
            if (autor == null) {
                return false;
            }
            if (!autores.contains(autor)) {
                autores.add(autor);
            }
        }

        libro.setCategoria(categoria);
        libro.setEditorial(editorial);
        libro.setAutores(autores);
        return true;
    }
}