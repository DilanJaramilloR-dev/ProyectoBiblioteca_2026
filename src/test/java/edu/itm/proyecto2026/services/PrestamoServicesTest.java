package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Bibliotecario;
import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaEntities.Usuario;
import edu.itm.proyecto2026.jpaRepositories.BibliotecarioRepository;
import edu.itm.proyecto2026.jpaRepositories.EjemplarRepository;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
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
class PrestamoServicesTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private EjemplarRepository ejemplarRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BibliotecarioRepository bibliotecarioRepository;

    @InjectMocks
    private PrestamoServices prestamoServices;

    @Test
    void getPrestamos_returnsAllPrestamos() {
        Prestamo prestamo = crearPrestamo();
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoServices.getPrestamos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("activo", resultado.get(0).getEstadoPrestamo());
    }

    @Test
    void insertarPrestamo_whenEjemplarUsuarioAndBibliotecarioExist_returnsSavedPrestamo() {
        Prestamo prestamo = crearPrestamo();
        when(ejemplarRepository.findById(1)).thenReturn(Optional.of(prestamo.getEjemplar()));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(prestamo.getUsuario()));
        when(bibliotecarioRepository.findById(1)).thenReturn(Optional.of(prestamo.getBibliotecario()));
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(prestamo.getEjemplar());

        Prestamo resultado = prestamoServices.insertarPrestamo(prestamo);

        assertNotNull(resultado);
        assertEquals("activo", resultado.getEstadoPrestamo());
    }

    @Test
    void devolverPrestamo_whenPrestamoExists_returnsUpdatedPrestamo() {
        Prestamo prestamo = crearPrestamo();
        prestamo.setFechaDevolucionPrevista(LocalDate.now().minusDays(2));
        when(prestamoRepository.findById(1)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        when(ejemplarRepository.save(any(Ejemplar.class))).thenReturn(prestamo.getEjemplar());

        Prestamo resultado = prestamoServices.devolverPrestamo(1);

        assertNotNull(resultado);
        assertEquals("devuelto", resultado.getEstadoPrestamo());
    }

    @Test
    void eliminarPrestamo_whenExists_returnsTrue() {
        when(prestamoRepository.existsById(1)).thenReturn(true);

        boolean resultado = prestamoServices.eliminarPrestamo(1);

        assertTrue(resultado);
        verify(prestamoRepository).deleteById(1);
    }

    private Prestamo crearPrestamo() {
        Persona personaUsuario = Persona.builder().idPersona(1).nombrePersona("Ana").apellidoPersona("Pérez").build();
        Persona personaBibliotecario = Persona.builder().idPersona(2).nombrePersona("Luis").apellidoPersona("Gómez").build();

        Usuario usuario = Usuario.builder().idUsuario(1).persona(personaUsuario).build();
        Bibliotecario bibliotecario = Bibliotecario.builder().idBibliotecario(1).persona(personaBibliotecario).build();
        Ejemplar ejemplar = Ejemplar.builder().idEjemplar(1).codigoInventario("E-001").disponibilidadEjemplar("si").build();

        return Prestamo.builder()
                .idPrestamo(1)
                .ejemplar(ejemplar)
                .usuario(usuario)
                .bibliotecario(bibliotecario)
                .estadoPrestamo("activo")
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucionPrevista(LocalDate.now().plusDays(5))
                .build();
    }
}
