package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Bibliotecario;
import edu.itm.proyecto2026.services.BibliotecarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private BibliotecarioServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Bibliotecario>> getBibliotecarios() {
        try {
            return new ResponseEntity<>(service.getBibliotecarios(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Bibliotecario> insertarBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        if (ObjectUtils.isEmpty(bibliotecario)
                || ObjectUtils.isEmpty(bibliotecario.getNombrePersona())
                || ObjectUtils.isEmpty(bibliotecario.getApellidoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getCorreoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getTelefonoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getUsuarioLoginBibliotecario())
                || ObjectUtils.isEmpty(bibliotecario.getContrasenaBibliotecario())
                || ObjectUtils.isEmpty(bibliotecario.getDocumentoBibliotecario())) {
            return new ResponseEntity<>(bibliotecario, HttpStatus.BAD_REQUEST);
        }
        try {
            Bibliotecario guardado = service.insertarBibliotecario(bibliotecario);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(bibliotecario, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(bibliotecario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Bibliotecario> actualizarBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        if (ObjectUtils.isEmpty(bibliotecario)
                || ObjectUtils.isEmpty(bibliotecario.getIdBibliotecario())
                || ObjectUtils.isEmpty(bibliotecario.getNombrePersona())
                || ObjectUtils.isEmpty(bibliotecario.getApellidoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getCorreoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getTelefonoPersona())
                || ObjectUtils.isEmpty(bibliotecario.getUsuarioLoginBibliotecario())
                || ObjectUtils.isEmpty(bibliotecario.getDocumentoBibliotecario())) {
            return new ResponseEntity<>(bibliotecario, HttpStatus.BAD_REQUEST);
        }
        try {
            Bibliotecario guardado = service.actualizarBibliotecario(bibliotecario);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(bibliotecario, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(bibliotecario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Bibliotecario> getBibliotecario(@PathVariable Integer id) {
        try {
            Bibliotecario bibliotecario = service.getBibliotecario(id);
            if (bibliotecario != null) {
                return new ResponseEntity<>(bibliotecario, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarBibliotecario(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarBibliotecario(id);
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
