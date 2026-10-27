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

public class Autor {
    private Long idAutor;
    private String nombreAutor;
    private String apellidoAutor;
    private String nacionalidadAutor;
    private LocalDate fechaNacimiento;
}
