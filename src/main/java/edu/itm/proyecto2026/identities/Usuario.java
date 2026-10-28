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
public class Usuario {
    private Long idUsuario;
    private Long idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private String correoPersona;
    private String telefonoPersona;
    private String direccionUsuario;
    private LocalDate fechaRegistroUsuario;
}
