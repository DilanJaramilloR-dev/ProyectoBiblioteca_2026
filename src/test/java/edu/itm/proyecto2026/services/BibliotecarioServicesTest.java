package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Bibliotecario;
import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaRepositories.BibliotecarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BibliotecarioServicesTest {

    @Mock
    private BibliotecarioRepository bibliotecarioRepository;

    @InjectMocks
    private BibliotecarioServices bibliotecarioServices;

    @Test
    void getBibliotecarios_returnsAllBibliotecarios() {
        Bibliotecario bibliotecario = crearBibliotecario();
        when(bibliotecarioRepository.findAll()).thenReturn(List.of(bibliotecario));

        List<Bibliotecario> resultado = bibliotecarioServices.getBibliotecarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ana", resultado.get(0).getUsuarioLoginBibliotecario());
    }

    @Test
    void getBibliotecario_whenExists_returnsBibliotecario() {
        Bibliotecario bibliotecario = crearBibliotecario();
        when(bibliotecarioRepository.findById(1)).thenReturn(Optional.of(bibliotecario));

        Bibliotecario resultado = bibliotecarioServices.getBibliotecario(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdBibliotecario());
    }

    @Test
    void getBibliotecario_whenNotExists_returnsNull() {
        when(bibliotecarioRepository.findById(99)).thenReturn(Optional.empty());

        Bibliotecario resultado = bibliotecarioServices.getBibliotecario(99);

        assertNull(resultado);
    }

    @Test
    void insertarBibliotecario_whenValid_returnsSavedBibliotecario() {
        Bibliotecario bibliotecario = crearBibliotecario();
        when(bibliotecarioRepository.save(any(Bibliotecario.class))).thenReturn(bibliotecario);

        Bibliotecario resultado = bibliotecarioServices.insertarBibliotecario(bibliotecario);

        assertNotNull(resultado);
        assertEquals("ana", resultado.getUsuarioLoginBibliotecario());
        verify(bibliotecarioRepository).save(any(Bibliotecario.class));
    }

    @Test
    void actualizarBibliotecario_whenExists_updatesAndReturnsBibliotecario() {
        Bibliotecario existente = crearBibliotecario();
        Bibliotecario actualizado = crearBibliotecario();
        actualizado.setUsuarioLoginBibliotecario("ana2");
        actualizado.setDocumentoBibliotecario("999");

        when(bibliotecarioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(bibliotecarioRepository.save(any(Bibliotecario.class))).thenReturn(actualizado);

        Bibliotecario resultado = bibliotecarioServices.actualizarBibliotecario(actualizado);

        assertNotNull(resultado);
        assertEquals("ana2", resultado.getUsuarioLoginBibliotecario());
        verify(bibliotecarioRepository).save(existente);
    }

    @Test
    void eliminarBibliotecario_whenExists_returnsTrue() {
        when(bibliotecarioRepository.existsById(1)).thenReturn(true);

        boolean resultado = bibliotecarioServices.eliminarBibliotecario(1);

        assertTrue(resultado);
        verify(bibliotecarioRepository).deleteById(1);
    }

    @Test
    void eliminarBibliotecario_whenNotExists_returnsFalse() {
        when(bibliotecarioRepository.existsById(99)).thenReturn(false);

        boolean resultado = bibliotecarioServices.eliminarBibliotecario(99);

        assertFalse(resultado);
        verify(bibliotecarioRepository, never()).deleteById(anyInt());
    }

    private Bibliotecario crearBibliotecario() {
        Persona persona = Persona.builder()
                .idPersona(1)
                .nombrePersona("Ana")
                .apellidoPersona("García")
                .correoPersona("ana@mail.com")
                .telefonoPersona("123456")
                .build();

        return Bibliotecario.builder()
                .idBibliotecario(1)
                .persona(persona)
                .usuarioLoginBibliotecario("ana")
                .contrasenaBibliotecario("1234")
                .documentoBibliotecario("ABC123")
                .build();
    }
}
