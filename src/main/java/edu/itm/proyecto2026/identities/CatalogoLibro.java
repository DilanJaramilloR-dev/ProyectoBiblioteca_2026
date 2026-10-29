package edu.itm.proyecto2026.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoLibro {
    private Long idLibro;
    private String tituloLibro;
    private String isbnLibro;
    private String descripcionLibro;
    private Integer anioPublicacionLibro;
    private String nombreCategoria;
    private String nombreEditorial;
    private String autores;
    private Integer ejemplaresDisponibles;
}