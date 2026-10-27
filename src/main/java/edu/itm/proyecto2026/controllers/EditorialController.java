package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Editorial;
import edu.itm.proyecto2026.services.EditorialServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Editorial>> getEditoriales() {
        try {
            return new ResponseEntity<>(service.getEditoriales(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Editorial> insertarEditorial(@RequestBody Editorial editorial) {
        if (ObjectUtils.isEmpty(editorial)
                || ObjectUtils.isEmpty(editorial.getCorreoEditorial())
                || ObjectUtils.isEmpty(editorial.getNombreEditorial())
                || ObjectUtils.isEmpty(editorial.getPaisEditorial())
                || ObjectUtils.isEmpty(editorial.getTelefonoEditorial())) {
            return new ResponseEntity<>(editorial, HttpStatus.BAD_REQUEST);
        }
        try {
            Editorial guardado = service.insertarEditorial(editorial);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(editorial, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(editorial, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Editorial> actualizarEditorial(@RequestBody Editorial editorial) {
        if (ObjectUtils.isEmpty(editorial)
                || ObjectUtils.isEmpty(editorial.getIdEditorial())
                || ObjectUtils.isEmpty(editorial.getCorreoEditorial())
                || ObjectUtils.isEmpty(editorial.getNombreEditorial())
                || ObjectUtils.isEmpty(editorial.getPaisEditorial())
                || ObjectUtils.isEmpty(editorial.getTelefonoEditorial())) {
            return new ResponseEntity<>(editorial, HttpStatus.BAD_REQUEST);
        }
        try {
            Editorial guardado = service.actualizarEditorial(editorial);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(editorial, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(editorial, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Editorial> getEditorial(@PathVariable Integer id) {
        try {
            Editorial editorial = service.getEditorial(id);
            if (editorial != null) {
                return new ResponseEntity<>(editorial, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEditorial(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarEditorial(id);
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
