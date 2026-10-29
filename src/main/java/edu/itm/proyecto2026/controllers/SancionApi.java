package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Sancion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SancionApi {

    @Operation(
            tags = {"Sanciones"},
            summary = "Obtiene la lista de sanciones",
            description = "Obtiene la lista de sanciones registradas en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de sanciones obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Sancion.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Sancion.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Sancion>> getSanciones();

    @Operation(
            tags = {"Sanciones"},
            summary = "Crea una sanción",
            description = "Crea una nueva sanción para un préstamo. Si no se envía la fecha de inicio se usa la de hoy, y si no se envía el estado queda como 'activa'.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Sanción creada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (el préstamo no existe)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Sancion> insertarSancion(@RequestBody Sancion sancion);

    @Operation(
            tags = {"Sanciones"},
            summary = "Actualiza una sanción",
            description = "Actualiza el tipo, el motivo, la fecha de fin y el estado de una sanción. El préstamo y la fecha de inicio no se modifican.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Sanción actualizada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato inválido)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Sancion> actualizarSancion(@RequestBody Sancion sancion);

    @Operation(
            tags = {"Sanciones"},
            summary = "Consulta una sanción por id",
            description = "Busca una sanción por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sanción encontrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Sancion.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Sanción no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Sancion> getSancion(@PathVariable Integer id);
}
