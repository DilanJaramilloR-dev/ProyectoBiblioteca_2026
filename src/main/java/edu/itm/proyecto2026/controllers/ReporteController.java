package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import edu.itm.proyecto2026.services.ReporteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteServiceInterface service;

    @GetMapping("/prestamos-activos")
    public ResponseEntity<List<ReportePrestamo>> getPrestamosActivos() {
        try {
            return new ResponseEntity<>(service.getPrestamosActivos(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prestamos-vencidos")
    public ResponseEntity<List<ReportePrestamo>> getPrestamosVencidos() {
        try {
            return new ResponseEntity<>(service.getPrestamosVencidos(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inventario")
    public ResponseEntity<List<ReporteInventario>> getInventario() {
        try {
            return new ResponseEntity<>(service.getInventario(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
