package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Sancion;
import edu.itm.proyecto2026.services.SancionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanciones")
public class SancionController {

    @Autowired
    private SancionServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Sancion>> getSanciones() {
        try {
            return new ResponseEntity<>(service.getSanciones(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Sancion> insertarSancion(@RequestBody Sancion sancion) {
        if (ObjectUtils.isEmpty(sancion)
                || ObjectUtils.isEmpty(sancion.getIdPrestamo())
                || ObjectUtils.isEmpty(sancion.getTipoSancion())) {
            return new ResponseEntity<>(sancion, HttpStatus.BAD_REQUEST);
        }
        try {
            Sancion guardado = service.insertarSancion(sancion);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(sancion, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(sancion, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Sancion> actualizarSancion(@RequestBody Sancion sancion) {
        if (ObjectUtils.isEmpty(sancion)
                || ObjectUtils.isEmpty(sancion.getIdSancion())
                || ObjectUtils.isEmpty(sancion.getTipoSancion())
                || ObjectUtils.isEmpty(sancion.getEstadoSancion())) {
            return new ResponseEntity<>(sancion, HttpStatus.BAD_REQUEST);
        }
        try {
            Sancion guardado = service.actualizarSancion(sancion);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(sancion, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(sancion, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Sancion> getSancion(@PathVariable Integer id) {
        try {
            Sancion sancion = service.getSancion(id);
            if (sancion != null) {
                return new ResponseEntity<>(sancion, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
