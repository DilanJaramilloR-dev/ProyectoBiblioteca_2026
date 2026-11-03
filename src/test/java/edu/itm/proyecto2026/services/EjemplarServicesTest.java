package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.EjemplarRepository;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
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
class EjemplarServicesTest {

    @Mock
    private EjemplarRepository ejemplarRepository;

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private EjemplarServices ejemplarServices;

    @Test
    void getEjemplares_returnsAllEjemplares() {
        Ejemplar ejemplar = crearEjemplar();
        when(ejemplarRepository.findAll()).thenReturn(List.of(ejemplar));

        List<Ejemplar> resultado = ejemplarServices.getEjemplares();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("E-001", resultado.get(0).getCodigoInventario());
    }

    @Test
    void insertarEjemplar_whenLibroExists_returnsSavedEjemplar() {
        Libro libro = crearLibro();
        Ejemplar ejemplar = crearEjemplar();
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(ejemplar);

        Ejemplar resultado = ejemplarServices.insertarEjemplar(ejemplar);

        assertNotNull(resultado);
        assertEquals("si", resultado.getDisponibilidadEjemplar());
        verify(ejemplarRepository).save(any(Ejemplar.class));
    }

    @Test
    void insertarEjemplar_whenLibroDoesNotExist_returnsNull() {
        Ejemplar ejemplar = crearEjemplar();
        ejemplar.getLibro().setIdLibro(999);
        when(libroRepository.findById(999)).thenReturn(Optional.empty());

        Ejemplar resultado = ejemplarServices.insertarEjemplar(ejemplar);

        assertNull(resultado);
    }

    @Test
    void actualizarEjemplar_whenExists_returnsUpdatedEjemplar() {
        Libro libro = crearLibro();
        Ejemplar existente = crearEjemplar();
        Ejemplar actualizado = crearEjemplar();
        actualizado.setCodigoInventario("E-002");

        when(ejemplarRepository.findById(1)).thenReturn(Optional.of(existente));
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(actualizado);

        Ejemplar resultado = ejemplarServices.actualizarEjemplar(actualizado);

        assertNotNull(resultado);
        assertEquals("E-002", resultado.getCodigoInventario());
    }

    @Test
    void eliminarEjemplar_whenExists_returnsTrue() {
        when(ejemplarRepository.existsById(1)).thenReturn(true);

        boolean resultado = ejemplarServices.eliminarEjemplar(1);

        assertTrue(resultado);
        verify(ejemplarRepository).deleteById(1);
    }

    private Libro crearLibro() {
        Libro libro = new Libro();
        libro.setIdLibro(1);
        libro.setTituloLibro("Spring Boot");
        return libro;
    }

    private Ejemplar crearEjemplar() {
        Libro libro = crearLibro();
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIdEjemplar(1);
        ejemplar.setLibro(libro);
        ejemplar.setCodigoInventario("E-001");
        ejemplar.setFechaAdquisicion(LocalDate.of(2025, 2, 10));
        ejemplar.setEstadoEjemplar("bueno");
        ejemplar.setUbicacionEjemplar("Sala 1");
        ejemplar.setDisponibilidadEjemplar("si");
        return ejemplar;
    }
}
