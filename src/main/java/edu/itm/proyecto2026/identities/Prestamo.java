package edu.itm.proyecto2026.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    private Long idPrestamo;
    private Long idEjemplar;
    private Long idUsuario;
    private Long idBibliotecario;
    private String estadoPrestamo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
}
