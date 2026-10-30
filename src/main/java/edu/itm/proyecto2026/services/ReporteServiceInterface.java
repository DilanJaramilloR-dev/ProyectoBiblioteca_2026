package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;

import java.util.List;

public interface ReporteServiceInterface {
    List<ReportePrestamo> getPrestamosActivos();
    List<ReportePrestamo> getPrestamosVencidos();
    List<ReporteInventario> getInventario();
}