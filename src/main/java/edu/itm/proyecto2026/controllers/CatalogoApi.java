package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.CatalogoLibro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CatalogoApi {

    @Operation(
            tags = {"Catalogo"},
            summary = "Consulta el catálogo de libros disponibles",
            description = "Lista los libros que tienen al menos un ejemplar disponible, ordenados por título. Se puede filtrar por título, autor o categoría (coincidencia parcial), solos o combinados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Catálogo obtenido correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CatalogoLibro.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CatalogoLibro.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<CatalogoLibro>> getCatalogo(
            @Parameter(description = "Parte del título del libro")
            @RequestParam(name = "titulo", required = false) String titulo,
            @Parameter(description = "Parte del nombre o apellido del autor")
            @RequestParam(name = "autor", required = false) String autor,
            @Parameter(description = "Parte del nombre de la categoría")
            @RequestParam(name = "categoria", required = false) String categoria);

    @Operation(
            tags = {"Catalogo"},
            summary = "Consulta un libro del catálogo por id",
            description = "Busca un libro por su identificador, aunque no tenga ejemplares disponibles (en ese caso ejemplaresDisponibles es 0).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Libro encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CatalogoLibro.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<CatalogoLibro> getCatalogoLibro(@PathVariable Integer id);
}