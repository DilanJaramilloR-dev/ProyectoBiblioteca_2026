package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Editorial;
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

public interface EditorialApi {

    @Operation(
            tags = {"Editoriales"},
            summary = "Obtiene la lista de editoriales",
            description = "Obtiene la lista de editoriales registradas en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de editoriales obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Editorial.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Editorial.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Editorial>> getEditoriales();

    @Operation(
            tags = {"Editoriales"},
            summary = "Crea una editorial",
            description = "Crea una nueva editorial en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Editorial creada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (por ejemplo, un dato duplicado o una referencia que no existe)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Editorial> insertarEditorial(@RequestBody Editorial editorial);

    @Operation(
            tags = {"Editoriales"},
            summary = "Actualiza una editorial",
            description = "Actualiza la información de una editorial existente.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Editorial actualizada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato inválido)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Editorial> actualizarEditorial(@RequestBody Editorial editorial);

    @Operation(
            tags = {"Editoriales"},
            summary = "Consulta una editorial por id",
            description = "Busca una editorial por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Editorial encontrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Editorial.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Editorial no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Editorial> getEditorial(@PathVariable Integer id);

    @Operation(
            tags = {"Editoriales"},
            summary = "Elimina una editorial",
            description = "Elimina una editorial por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Editorial eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Editorial no encontrada (o no se puede eliminar porque tiene datos relacionados)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarEditorial(@PathVariable Integer id);
}