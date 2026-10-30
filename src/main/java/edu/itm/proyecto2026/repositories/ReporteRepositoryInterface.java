package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;

import java.util.List;

public interface ReporteRepositoryInterface {
    List<ReportePrestamo> getPrestamosActivos();
    List<ReportePrestamo> getPrestamosVencidos();
    List<ReporteInventario> getInventario();
}