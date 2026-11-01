package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.CatalogoLibro;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoServices implements CatalogoServiceInterface {

    private final LibroRepository libroRepository;

    public CatalogoServices(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<CatalogoLibro> getCatalogo(String titulo, String autor, String categoria) {
        return libroRepository
                .buscarCatalogo(normalizar(titulo), normalizar(autor), normalizar(categoria))
                .stream()
                .map(this::aCatalogoLibro)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CatalogoLibro getCatalogoLibro(Integer id) {
        Libro libro = libroRepository.findById(id).orElse(null);
        return libro == null ? null : aCatalogoLibro(libro);
    }

    private String normalizar(String valor) {
        return (valor == null || valor.isBlank()) ? "" : valor.trim();
    }

    private CatalogoLibro aCatalogoLibro(Libro libro) {
        String autores = libro.getAutores().stream()
                .map(autor -> autor.getNombreAutor() + " " + autor.getApellidoAutor())
                .collect(Collectors.joining(", "));
        int disponibles = (int) libro.getEjemplares().stream()
                .filter(ejemplar -> "si".equalsIgnoreCase(ejemplar.getDisponibilidadEjemplar()))
                .count();
        return CatalogoLibro.builder()
                .idLibro(libro.getIdLibro().longValue())
                .tituloLibro(libro.getTituloLibro())
                .isbnLibro(libro.getIsbnLibro())
                .descripcionLibro(libro.getDescripcionLibro())
                .anioPublicacionLibro(libro.getAnioPublicacionLibro())
                .nombreCategoria(libro.getCategoria().getNombreCategoria())
                .nombreEditorial(libro.getEditorial().getNombreEditorial())
                .autores(autores)
                .ejemplaresDisponibles(disponibles)
                .build();
    }
}