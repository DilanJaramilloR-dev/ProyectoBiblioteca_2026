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
public class ReportePrestamo {
    private Long idPrestamo;
    private String codigoInventario;
    private String tituloLibro;
    private String nombreUsuario;
    private String nombreBibliotecario;
    private String estadoPrestamo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private Integer diasRetraso;
}
