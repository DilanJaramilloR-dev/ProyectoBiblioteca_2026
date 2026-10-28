package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Ejemplar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EjemplarApi {

    @Operation(
            tags = {"Ejemplares"},
            summary = "Obtiene la lista de ejemplares",
            description = "Obtiene la lista de ejemplares registrados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ejemplares obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Ejemplar.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Ejemplar.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Ejemplar>> getEjemplares();

    @Operation(
            tags = {"Ejemplares"},
            summary = "Crea un ejemplar",
            description = "Crea un nuevo ejemplar. Siempre queda disponible ('si'), sin importar el valor enviado.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ejemplar creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (código de inventario duplicado o libro que no existe)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Ejemplar> insertarEjemplar(@RequestBody Ejemplar ejemplar);

    @Operation(
            tags = {"Ejemplares"},
            summary = "Actualiza un ejemplar",
            description = "Actualiza la información de un ejemplar existente. La disponibilidad no se modifica desde aquí.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Ejemplar actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato inválido)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Ejemplar> actualizarEjemplar(@RequestBody Ejemplar ejemplar);

    @Operation(
            tags = {"Ejemplares"},
            summary = "Consulta un ejemplar por id",
            description = "Busca un ejemplar por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ejemplar encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ejemplar.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id);

    @Operation(
            tags = {"Ejemplares"},
            summary = "Elimina un ejemplar",
            description = "Elimina un ejemplar por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ejemplar eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado (o no se puede eliminar porque tiene préstamos)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarEjemplar(@PathVariable Integer id);
}
