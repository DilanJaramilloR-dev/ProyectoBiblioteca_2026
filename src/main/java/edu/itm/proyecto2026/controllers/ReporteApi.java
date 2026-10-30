package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ReporteApi {

    @Operation(
            tags = {"Reportes"},
            summary = "Reporte de préstamos activos",
            description = "Lista los préstamos sin devolver que están en estado activo y cuya fecha de devolución prevista todavía no ha pasado, ordenados por fecha prevista.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reporte obtenido correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportePrestamo.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportePrestamo.class))
                            )
                    )
            }
    )
    @GetMapping("/prestamos-activos")
    ResponseEntity<List<ReportePrestamo>> getPrestamosActivos();

    @Operation(
            tags = {"Reportes"},
            summary = "Reporte de préstamos vencidos",
            description = "Lista los préstamos sin devolver que están en estado vencido o cuya fecha de devolución prevista ya pasó, del más atrasado al menos atrasado, con los días de retraso calculados a la fecha de hoy.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reporte obtenido correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportePrestamo.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportePrestamo.class))
                            )
                    )
            }
    )
    @GetMapping("/prestamos-vencidos")
    ResponseEntity<List<ReportePrestamo>> getPrestamosVencidos();

    @Operation(
            tags = {"Reportes"},
            summary = "Reporte de inventario general",
            description = "Lista todos los libros con su categoría, el total de ejemplares, cuántos están disponibles y cuántos no.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reporte obtenido correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReporteInventario.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReporteInventario.class))
                            )
                    )
            }
    )
    @GetMapping("/inventario")
    ResponseEntity<List<ReporteInventario>> getInventario();
}