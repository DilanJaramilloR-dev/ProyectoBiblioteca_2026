package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Libro;
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

public interface LibroApi {

    @Operation(
            tags = {"Libros"},
            summary = "Obtiene la lista de libros",
            description = "Obtiene la lista de libros registrados en la base de datos, con los ids de sus autores.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de libros obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Libro.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Libro.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Libro>> getLibros();

    @Operation(
            tags = {"Libros"},
            summary = "Crea un libro",
            description = "Crea un nuevo libro con sus autores (idsAutores) en una sola transacción.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Libro creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios (incluye al menos un autor)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (ISBN duplicado, o categoría, editorial o autor que no existe)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Libro> insertarLibro(@RequestBody Libro libro);

    @Operation(
            tags = {"Libros"},
            summary = "Actualiza un libro",
            description = "Actualiza la información de un libro existente y reemplaza su lista de autores.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Libro actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato inválido)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Libro> actualizarLibro(@RequestBody Libro libro);

    @Operation(
            tags = {"Libros"},
            summary = "Consulta un libro por id",
            description = "Busca un libro por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Libro encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Libro.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Libro> getLibro(@PathVariable Integer id);

    @Operation(
            tags = {"Libros"},
            summary = "Elimina un libro",
            description = "Elimina un libro por su identificador (también se borran sus filas en autor_libro y sus ejemplares).",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Libro no encontrado (o no se puede eliminar porque tiene datos relacionados)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarLibro(@PathVariable Integer id);
}