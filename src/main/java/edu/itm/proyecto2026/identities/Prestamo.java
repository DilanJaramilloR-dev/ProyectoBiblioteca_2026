package edu.itm.proyecto2026.identities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Prestamo {
    private Long idPrestamo;
    private Long idUsuario;
    private Long idBibliotecario;
    private Long idEjemplar;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estadoPrestamo;
}
