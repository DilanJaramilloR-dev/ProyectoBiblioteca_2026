package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaRepositories.AutorRepository;
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
class AutorServicesTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorServices autorServices;

    @Test
    void getAutor_returnsAllAutores() {
        Autor autor = crearAutor();
        when(autorRepository.findAll()).thenReturn(List.of(autor));

        List<Autor> resultado = autorServices.getAutor();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Gabriel", resultado.get(0).getNombreAutor());
    }

    @Test
    void getAutor_whenExists_returnsAutor() {
        Autor autor = crearAutor();
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Autor resultado = autorServices.getAutor(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAutor());
    }

    @Test
    void insertarAutor_setsIdNullAndSaves() {
        Autor autor = crearAutor();
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        Autor resultado = autorServices.insertarAutor(autor);

        assertNotNull(resultado);
        assertEquals("Gabriel", resultado.getNombreAutor());
        verify(autorRepository).save(any(Autor.class));
    }

    @Test
    void actualizarAutor_whenExists_returnsSavedAutor() {
        Autor autor = crearAutor();
        when(autorRepository.existsById(1)).thenReturn(true);
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        Autor resultado = autorServices.actualizarAutor(autor);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAutor());
    }

    @Test
    void eliminarAutor_whenExists_returnsTrue() {
        when(autorRepository.existsById(1)).thenReturn(true);

        boolean resultado = autorServices.eliminarAutor(1);

        assertTrue(resultado);
        verify(autorRepository).deleteById(1);
    }

    private Autor crearAutor() {
        return Autor.builder()
                .idAutor(1)
                .nombreAutor("Gabriel")
                .apellidoAutor("García")
                .nacionalidadAutor("Colombiana")
                .fechaNacimiento(LocalDate.of(1980, 5, 10))
                .build();
    }
}
