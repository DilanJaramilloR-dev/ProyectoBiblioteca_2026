package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaEntities.Usuario;
import edu.itm.proyecto2026.jpaRepositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServicesTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServices usuarioServices;

    @Test
    void getUsuarios_returnsAllUsuarios() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioServices.getUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Calle 1", resultado.get(0).getDireccionUsuario());
    }

    @Test
    void getUsuario_whenExists_returnsUsuario() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioServices.getUsuario(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    void insertarUsuario_whenValid_returnsSavedUsuario() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioServices.insertarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Calle 1", resultado.getDireccionUsuario());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void actualizarUsuario_whenExists_updatesAndReturnsUsuario() {
        Usuario existente = crearUsuario();
        Usuario actualizado = crearUsuario();
        actualizado.setDireccionUsuario("Calle 2");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario resultado = usuarioServices.actualizarUsuario(actualizado);

        assertNotNull(resultado);
        assertEquals("Calle 2", resultado.getDireccionUsuario());
    }

    @Test
    void eliminarUsuario_whenNotExists_returnsFalse() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        boolean resultado = usuarioServices.eliminarUsuario(99);

        assertFalse(resultado);
    }

    private Usuario crearUsuario() {
        Persona persona = Persona.builder()
                .idPersona(1)
                .nombrePersona("Luis")
                .apellidoPersona("Pérez")
                .correoPersona("luis@mail.com")
                .telefonoPersona("300000")
                .build();

        return Usuario.builder()
                .idUsuario(1)
                .persona(persona)
                .direccionUsuario("Calle 1")
                .fechaRegistroUsuario(LocalDate.of(2025, 1, 12))
                .build();
    }
}
