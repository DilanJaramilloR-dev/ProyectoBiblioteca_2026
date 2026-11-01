package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Bibliotecario;
import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaRepositories.BibliotecarioRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliotecarioServices implements BibliotecarioServiceInterface {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioServices(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarioRepository.findAll();
    }

    public Bibliotecario getBibliotecario(Integer id) {
        return bibliotecarioRepository.findById(id).orElse(null);
    }

    public Bibliotecario insertarBibliotecario(Bibliotecario bibliotecario) {
        try {
            Persona persona = Persona.builder()
                    .nombrePersona(bibliotecario.getPersona().getNombrePersona())
                    .apellidoPersona(bibliotecario.getPersona().getApellidoPersona())
                    .correoPersona(bibliotecario.getPersona().getCorreoPersona())
                    .telefonoPersona(bibliotecario.getPersona().getTelefonoPersona())
                    .build();
            Bibliotecario nuevo = Bibliotecario.builder()
                    .persona(persona)
                    .usuarioLoginBibliotecario(bibliotecario.getUsuarioLoginBibliotecario())
                    .contrasenaBibliotecario(bibliotecario.getContrasenaBibliotecario())
                    .documentoBibliotecario(bibliotecario.getDocumentoBibliotecario())
                    .build();
            return bibliotecarioRepository.save(nuevo);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Bibliotecario actualizarBibliotecario(Bibliotecario bibliotecario) {
        try {
            Bibliotecario existente = bibliotecarioRepository
                    .findById(bibliotecario.getIdBibliotecario()).orElse(null);
            if (existente == null) {
                return null;
            }
            existente.getPersona().setNombrePersona(bibliotecario.getPersona().getNombrePersona());
            existente.getPersona().setApellidoPersona(bibliotecario.getPersona().getApellidoPersona());
            existente.getPersona().setCorreoPersona(bibliotecario.getPersona().getCorreoPersona());
            existente.getPersona().setTelefonoPersona(bibliotecario.getPersona().getTelefonoPersona());
            existente.setUsuarioLoginBibliotecario(bibliotecario.getUsuarioLoginBibliotecario());
            existente.setDocumentoBibliotecario(bibliotecario.getDocumentoBibliotecario());
            if (bibliotecario.getContrasenaBibliotecario() != null
                    && !bibliotecario.getContrasenaBibliotecario().isBlank()) {
                existente.setContrasenaBibliotecario(bibliotecario.getContrasenaBibliotecario());
            }
            return bibliotecarioRepository.save(existente);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarBibliotecario(Integer id) {
        try {
            if (!bibliotecarioRepository.existsById(id)) {
                return false;
            }
            bibliotecarioRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}