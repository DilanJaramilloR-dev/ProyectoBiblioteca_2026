package edu.itm.proyecto2026.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long idLibro;
    private Long idCategoria;
    private Long idEditorial;
    private String tituloLibro;
    private String descripcionLibro;
    private String isbnLibro;
    private Integer anioPublicacionLibro;
    private List<Long> idsAutores;
}
