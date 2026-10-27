package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Categoria;
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

public interface CategoriaApi {

    @Operation(
            tags = {"Categorias"},
            summary = "Obtiene la lista de categorías",
            description = "Obtiene la lista de categorías registradas en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de categorías obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Categoria.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Categoria.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Categoria>> getCategorias();

    @Operation(
            tags = {"Categorias"},
            summary = "Crea una categoría",
            description = "Crea una nueva categoría en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Categoría creada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (por ejemplo, un dato duplicado o una referencia que no existe)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Categoria> insertarCategoria(@RequestBody Categoria categoria);

    @Operation(
            tags = {"Categorias"},
            summary = "Actualiza una categoría",
            description = "Actualiza la información de una categoría existente.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Categoría actualizada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o hay un dato inválido)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Categoria> actualizarCategoria(@RequestBody Categoria categoria);

    @Operation(
            tags = {"Categorias"},
            summary = "Consulta una categoría por id",
            description = "Busca una categoría por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría encontrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Categoria.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Categoria> getCategoria(@PathVariable Integer id);

    @Operation(
            tags = {"Categorias"},
            summary = "Elimina una categoría",
            description = "Elimina una categoría por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada (o no se puede eliminar porque tiene datos relacionados)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id);
}
