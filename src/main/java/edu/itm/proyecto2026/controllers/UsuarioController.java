package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Usuario;
import edu.itm.proyecto2026.services.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceInterface service;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        try {
            return new ResponseEntity<>(service.getUsuarios(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Usuario> insertarUsuario(@RequestBody Usuario usuario) {
        if (ObjectUtils.isEmpty(usuario)
                || ObjectUtils.isEmpty(usuario.getNombrePersona())
                || ObjectUtils.isEmpty(usuario.getApellidoPersona())
                || ObjectUtils.isEmpty(usuario.getCorreoPersona())
                || ObjectUtils.isEmpty(usuario.getTelefonoPersona())) {
            return new ResponseEntity<>(usuario, HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario guardado = service.insertarUsuario(usuario);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(usuario, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        if (ObjectUtils.isEmpty(usuario)
                || ObjectUtils.isEmpty(usuario.getIdUsuario())
                || ObjectUtils.isEmpty(usuario.getNombrePersona())
                || ObjectUtils.isEmpty(usuario.getApellidoPersona())
                || ObjectUtils.isEmpty(usuario.getCorreoPersona())
                || ObjectUtils.isEmpty(usuario.getTelefonoPersona())) {
            return new ResponseEntity<>(usuario, HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario guardado = service.actualizarUsuario(usuario);
            if (guardado != null) {
                return new ResponseEntity<>(guardado, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(usuario, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = service.getUsuario(id);
            if (usuario != null) {
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarUsuario(id);
            if (eliminado) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
