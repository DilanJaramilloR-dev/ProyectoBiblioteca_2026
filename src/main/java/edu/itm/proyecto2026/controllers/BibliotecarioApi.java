package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Bibliotecario;
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

public interface BibliotecarioApi {

    @Operation(
            tags = {"Bibliotecarios"},
            summary = "Obtiene la lista de bibliotecarios",
            description = "Obtiene la lista de bibliotecarios registrados, con sus datos de persona. La contraseña nunca se devuelve.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de bibliotecarios obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Bibliotecario.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Bibliotecario.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Bibliotecario>> getBibliotecarios();

    @Operation(
            tags = {"Bibliotecarios"},
            summary = "Crea un bibliotecario",
            description = "Crea una persona y su bibliotecario en una sola transacción.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Bibliotecario creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (correo, usuario de login o documento ya registrados)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Bibliotecario> insertarBibliotecario(@RequestBody Bibliotecario bibliotecario);

    @Operation(
            tags = {"Bibliotecarios"},
            summary = "Actualiza un bibliotecario",
            description = "Actualiza los datos de la persona, el usuario de login y el documento. La contraseña solo cambia si se envía.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Bibliotecario actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato duplicado)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Bibliotecario> actualizarBibliotecario(@RequestBody Bibliotecario bibliotecario);

    @Operation(
            tags = {"Bibliotecarios"},
            summary = "Consulta un bibliotecario por id",
            description = "Busca un bibliotecario por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bibliotecario encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Bibliotecario.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Bibliotecario no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Bibliotecario> getBibliotecario(@PathVariable Integer id);

    @Operation(
            tags = {"Bibliotecarios"},
            summary = "Elimina un bibliotecario",
            description = "Elimina al bibliotecario y su persona por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Bibliotecario eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Bibliotecario no encontrado (o no se puede eliminar porque tiene préstamos)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarBibliotecario(@PathVariable Integer id);
}
