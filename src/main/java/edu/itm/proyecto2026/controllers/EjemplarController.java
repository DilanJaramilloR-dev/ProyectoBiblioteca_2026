package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Ejemplar;
import edu.itm.proyecto2026.services.EjemplarServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ejemplares")
public class EjemplarController {

    @Autowired
    private EjemplarServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Ejemplar>> getEjemplares() {
        try {
            return new ResponseEntity<>(service.getEjemplares(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Ejemplar> insertarEjemplar(@RequestBody Ejemplar ejemplar) {
        if (ObjectUtils.isEmpty(ejemplar)
                || ObjectUtils.isEmpty(ejemplar.getIdLibro())
                || ObjectUtils.isEmpty(ejemplar.getCodigoInventario())
                || ObjectUtils.isEmpty(ejemplar.getFechaAdquisicion())
                || ObjectUtils.isEmpty(ejemplar.getEstadoEjemplar())) {
            return new ResponseEntity<>(ejemplar, HttpStatus.BAD_REQUEST);
        }
        try {
            Ejemplar guardado = service.insertarEjemplar(ejemplar);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(ejemplar, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(ejemplar, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Ejemplar> actualizarEjemplar(@RequestBody Ejemplar ejemplar) {
        if (ObjectUtils.isEmpty(ejemplar)
                || ObjectUtils.isEmpty(ejemplar.getIdEjemplar())
                || ObjectUtils.isEmpty(ejemplar.getIdLibro())
                || ObjectUtils.isEmpty(ejemplar.getCodigoInventario())
                || ObjectUtils.isEmpty(ejemplar.getFechaAdquisicion())
                || ObjectUtils.isEmpty(ejemplar.getEstadoEjemplar())) {
            return new ResponseEntity<>(ejemplar, HttpStatus.BAD_REQUEST);
        }
        try {
            Ejemplar guardado = service.actualizarEjemplar(ejemplar);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(ejemplar, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(ejemplar, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id) {
        try {
            Ejemplar ejemplar = service.getEjemplar(id);
            if (ejemplar != null) {
                return new ResponseEntity<>(ejemplar, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarEjemplar(id);
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
