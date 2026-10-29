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
public class Sancion {
    private Long idSancion;
    private Long idPrestamo;
    private String tipoSancion;
    private String motivoSancion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoSancion;
}