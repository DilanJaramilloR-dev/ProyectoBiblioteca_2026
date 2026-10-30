package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import edu.itm.proyecto2026.repositories.ReporteRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServices implements ReporteServiceInterface {

    private final ReporteRepositoryInterface reporteRepository;

    public ReporteServices(ReporteRepositoryInterface reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<ReportePrestamo> getPrestamosActivos() {
        return reporteRepository.getPrestamosActivos();
    }

    public List<ReportePrestamo> getPrestamosVencidos() {
        return reporteRepository.getPrestamosVencidos();
    }

    public List<ReporteInventario> getInventario() {
        return reporteRepository.getInventario();
    }
}
