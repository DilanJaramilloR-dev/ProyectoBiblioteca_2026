package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Usuario;
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

public interface UsuarioApi {

    @Operation(
            tags = {"Usuarios"},
            summary = "Obtiene la lista de usuarios",
            description = "Obtiene la lista de usuarios registrados, con sus datos de persona.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuarios obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
                            )
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Usuario>> getUsuarios();

    @Operation(
            tags = {"Usuarios"},
            summary = "Crea un usuario",
            description = "Crea una persona y su usuario en una sola transacción. Si no se envía la fecha de registro, se usa la de hoy.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo crear (por ejemplo, el correo ya está registrado)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    )
            }
    )
    @PostMapping("/nuevo")
    ResponseEntity<Usuario> insertarUsuario(@RequestBody Usuario usuario);

    @Operation(
            tags = {"Usuarios"},
            summary = "Actualiza un usuario",
            description = "Actualiza los datos de la persona y la dirección del usuario. La fecha de registro no se modifica.",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Usuario actualizado correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, faltan datos obligatorios",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "No se pudo actualizar (el id no existe o el correo ya está registrado)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario);

    @Operation(
            tags = {"Usuarios"},
            summary = "Consulta un usuario por id",
            description = "Busca un usuario por su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Usuario.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping("/consultar/{id}")
    ResponseEntity<Usuario> getUsuario(@PathVariable Integer id);

    @Operation(
            tags = {"Usuarios"},
            summary = "Elimina un usuario",
            description = "Elimina al usuario y su persona por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado (o no se puede eliminar porque tiene préstamos)"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id);
}
