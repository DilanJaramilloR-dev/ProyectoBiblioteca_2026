package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Autor;
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

public interface AutorApi {

    @Operation(
            tags = {"Autores"},
            summary = "Obtiene la lista de autores",
            description = "Obtiene la lista de autores registrados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de autores obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Autor.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Autor.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Autor>> getAutores();

    @Operation(
            tags = {"Autores"},
            summary = "Crea un autor",
            description = "Crea un nuevo autor en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autor creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Autor.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, el objeto autor es nulo o no tiene nombre",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Autor.class)
                            )
                        ),
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Autor> insertarAutor(@RequestBody Autor autor);

    @Operation(
            tags = {"Autores"},
            summary = "Actualiza un autor",
            description = "Actualiza la información de un autor existente.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Autor actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Autor.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios del autor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Autor.class)
                            )
                    ),
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Autor> actualizarAutor(@RequestBody Autor autor);

    @Operation(
            tags = {"Autores"},
            summary = "Consulta un autor por id",
            description = "Busca un autor por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autor encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Autor.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Autor> getAutor(@PathVariable Integer id);

    @Operation(
            tags = {"Autores"},
            summary = "Elimina un autor",
            description = "Elimina un autor por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarAutor(@PathVariable Integer id);
}