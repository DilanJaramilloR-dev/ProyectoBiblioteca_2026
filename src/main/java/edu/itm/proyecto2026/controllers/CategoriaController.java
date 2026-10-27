package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Categoria;
import edu.itm.proyecto2026.services.CategoriaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Categoria>> getCategorias() {
        try {
            return new ResponseEntity<>(service.getCategorias(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Categoria> insertarCategoria(@RequestBody Categoria categoria) {
        if (ObjectUtils.isEmpty(categoria)
                || ObjectUtils.isEmpty(categoria.getNombreCategoria())
                || ObjectUtils.isEmpty(categoria.getDescripcionCategoria())) {
            return new ResponseEntity<>(categoria, HttpStatus.BAD_REQUEST);
        }
        try {
            Categoria guardado = service.insertarCategoria(categoria);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(categoria, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(categoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Categoria> actualizarCategoria(@RequestBody Categoria categoria) {
        if (ObjectUtils.isEmpty(categoria)
                || ObjectUtils.isEmpty(categoria.getIdCategoria())
                || ObjectUtils.isEmpty(categoria.getNombreCategoria())
                || ObjectUtils.isEmpty(categoria.getDescripcionCategoria())) {
            return new ResponseEntity<>(categoria, HttpStatus.BAD_REQUEST);
        }
        try {
            Categoria guardado = service.actualizarCategoria(categoria);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(categoria, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(categoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable Integer id) {
        try {
            Categoria categoria = service.getCategoria(id);
            if (categoria != null) {
                return new ResponseEntity<>(categoria, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarCategoria(id);
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
