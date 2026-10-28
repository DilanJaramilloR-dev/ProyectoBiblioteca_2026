package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Usuario;

import java.util.List;

public interface UsuarioServiceInterface {
    List<Usuario> getUsuarios();
    Usuario getUsuario(Integer id);
    Usuario insertarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(Integer id);
}
