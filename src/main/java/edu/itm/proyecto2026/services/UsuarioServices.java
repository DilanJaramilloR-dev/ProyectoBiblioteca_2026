package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaEntities.Usuario;
import edu.itm.proyecto2026.jpaRepositories.UsuarioRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioServices implements UsuarioServiceInterface {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuario(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario insertarUsuario(Usuario usuario) {
        try {
            Persona persona = Persona.builder()
                    .nombrePersona(usuario.getPersona().getNombrePersona())
                    .apellidoPersona(usuario.getPersona().getApellidoPersona())
                    .correoPersona(usuario.getPersona().getCorreoPersona())
                    .telefonoPersona(usuario.getPersona().getTelefonoPersona())
                    .build();
            Usuario nuevo = Usuario.builder()
                    .persona(persona)
                    .direccionUsuario(usuario.getDireccionUsuario())
                    .fechaRegistroUsuario(usuario.getFechaRegistroUsuario() != null
                            ? usuario.getFechaRegistroUsuario()
                            : LocalDate.now())
                    .build();
            return usuarioRepository.save(nuevo);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        try {
            Usuario existente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
            if (existente == null) {
                return null;
            }
            existente.getPersona().setNombrePersona(usuario.getPersona().getNombrePersona());
            existente.getPersona().setApellidoPersona(usuario.getPersona().getApellidoPersona());
            existente.getPersona().setCorreoPersona(usuario.getPersona().getCorreoPersona());
            existente.getPersona().setTelefonoPersona(usuario.getPersona().getTelefonoPersona());
            existente.setDireccionUsuario(usuario.getDireccionUsuario());
            return usuarioRepository.save(existente);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarUsuario(Integer id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                return false;
            }
            usuarioRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}