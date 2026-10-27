package edu.itm.proyecto2026.identities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {
    private Long idEditorial;
    private String correoEditorial;
    private String nombreEditorial;
    private String paisEditorial;
    private String telefonoEditorial;
}
