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
public class Ejemplar {
    private Long idEjemplar;
    private Long idLibro;
    private String codigoInventario;
    private LocalDate fechaAdquisicion;
    private String estadoEjemplar;
    private String ubicacionEjemplar;
    private String disponibilidadEjemplar;
}