package edu.itm.proyecto2026.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    private Long idCategoria;
    private String descripcionCategoria;
    private String nombreCategoria;
    private String estadoCategoria;
}
