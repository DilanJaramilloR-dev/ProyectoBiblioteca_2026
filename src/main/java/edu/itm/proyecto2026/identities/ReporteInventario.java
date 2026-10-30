package edu.itm.proyecto2026.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteInventario {
    private Long idLibro;
    private String tituloLibro;
    private String isbnLibro;
    private String nombreCategoria;
    private Integer totalEjemplares;
    private Integer ejemplaresDisponibles;
    private Integer ejemplaresNoDisponibles;
}
