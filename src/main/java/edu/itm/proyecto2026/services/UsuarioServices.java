package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Usuario;
import edu.itm.proyecto2026.repositories.UsuarioRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServices implements UsuarioServiceInterface {

    private final UsuarioRepositoryInterface usuarioRepository;

    public UsuarioServices(UsuarioRepositoryInterface usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

    public Usuario getUsuario(Integer id) {
        return usuarioRepository.getUsuario(id);
    }

    public Usuario insertarUsuario(Usuario usuario) {
        return usuarioRepository.insertarUsuario(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.actualizarUsuario(usuario);
    }

    public boolean eliminarUsuario(Integer id) {
        return usuarioRepository.eliminarUsuario(id);
    }
}
