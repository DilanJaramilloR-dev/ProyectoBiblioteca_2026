package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Usuario;

import java.util.List;

public interface UsuarioRepositoryInterface {
    List<Usuario> getUsuarios();
    Usuario getUsuario(Integer id);
    Usuario insertarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(Integer id);
}
