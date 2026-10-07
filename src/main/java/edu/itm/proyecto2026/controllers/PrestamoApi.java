package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Prestamo;
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

public interface PrestamoApi {

    @Operation(
            tags = {"Prestamos"},
            summary = "Obtiene la lista de préstamos",
            description = "Obtiene la lista de préstamos registrados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de préstamos obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Prestamo.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Prestamo.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Prestamo>> getPrestamos();

    @Operation(
            tags = {"Prestamos"},
            summary = "Consulta un préstamo por id",
            description = "Busca un préstamo por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Préstamo encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Prestamo> getPrestamo(@PathVariable Integer id);

    @Operation(
            tags = {"Prestamos"},
            summary = "Crea un préstamo",
            description = "Registra un nuevo préstamo y deja el ejemplar como no disponible.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Préstamo creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request: faltan el ejemplar, usuario o bibliotecario del préstamo",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),

                    
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Prestamo> insertarPrestamo(@RequestBody Prestamo prestamo);

    @Operation(
            tags = {"Prestamos"},
            summary = "Registra la devolución de un préstamo",
            description = "Registra la devolución y libera el ejemplar.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Devolución registrada correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            }
    )
    @PutMapping("/devolver/{id}")
    ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Integer id);

    @Operation(
            tags = {"Prestamos"},
            summary = "Actualiza un préstamo",
            description = "Actualiza la información de un préstamo existente.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Préstamo actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request: falta el identificador del préstamo",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Prestamo.class)
                            )
                    ),
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Prestamo> actualizarPrestamo(@RequestBody Prestamo prestamo);

    @Operation(
            tags = {"Prestamos"},
            summary = "Elimina un préstamo",
            description = "Elimina un préstamo por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Préstamo eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarPrestamo(@PathVariable Integer id);
}
