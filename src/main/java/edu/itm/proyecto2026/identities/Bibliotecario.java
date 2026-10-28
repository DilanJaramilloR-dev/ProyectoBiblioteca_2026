package edu.itm.proyecto2026.identities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bibliotecario {
    private Long idBibliotecario;
    private Long idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private String correoPersona;
    private String telefonoPersona;
    private String usuarioLoginBibliotecario;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenaBibliotecario;
    private String documentoBibliotecario;
}