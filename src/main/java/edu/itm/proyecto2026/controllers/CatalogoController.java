package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.CatalogoLibro;
import edu.itm.proyecto2026.services.CatalogoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<CatalogoLibro>> getCatalogo(
            @RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "autor", required = false) String autor,
            @RequestParam(name = "categoria", required = false) String categoria) {
        try {
            return new ResponseEntity<>(service.getCatalogo(titulo, autor, categoria), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<CatalogoLibro> getCatalogoLibro(@PathVariable Integer id) {
        try {
            CatalogoLibro libro = service.getCatalogoLibro(id);
            if (libro != null) {
                return new ResponseEntity<>(libro, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}