package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Libro;
import edu.itm.proyecto2026.services.LibroServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Libro>> getLibros() {
        try {
            return new ResponseEntity<>(service.getLibros(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Libro> insertarLibro(@RequestBody Libro libro) {
        if (ObjectUtils.isEmpty(libro)
                || ObjectUtils.isEmpty(libro.getIdCategoria())
                || ObjectUtils.isEmpty(libro.getIdEditorial())
                || ObjectUtils.isEmpty(libro.getTituloLibro())
                || ObjectUtils.isEmpty(libro.getIsbnLibro())
                || ObjectUtils.isEmpty(libro.getAnioPublicacionLibro())
                || ObjectUtils.isEmpty(libro.getIdsAutores())) {
            return new ResponseEntity<>(libro, HttpStatus.BAD_REQUEST);
        }
        try {
            Libro guardado = service.insertarLibro(libro);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(libro, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(libro, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Libro> actualizarLibro(@RequestBody Libro libro) {
        if (ObjectUtils.isEmpty(libro)
                || ObjectUtils.isEmpty(libro.getIdLibro())
                || ObjectUtils.isEmpty(libro.getIdCategoria())
                || ObjectUtils.isEmpty(libro.getIdEditorial())
                || ObjectUtils.isEmpty(libro.getTituloLibro())
                || ObjectUtils.isEmpty(libro.getIsbnLibro())
                || ObjectUtils.isEmpty(libro.getAnioPublicacionLibro())
                || ObjectUtils.isEmpty(libro.getIdsAutores())) {
            return new ResponseEntity<>(libro, HttpStatus.BAD_REQUEST);
        }
        try {
            Libro guardado = service.actualizarLibro(libro);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(libro, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(libro, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Integer id) {
        try {
            Libro libro = service.getLibro(id);
            if (libro != null) {
                return new ResponseEntity<>(libro, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarLibro(id);
            if (eliminado) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}