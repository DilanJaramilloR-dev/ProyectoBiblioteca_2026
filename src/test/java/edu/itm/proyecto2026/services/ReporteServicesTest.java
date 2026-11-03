package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaEntities.Bibliotecario;
import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaEntities.Persona;
import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaEntities.Usuario;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServicesTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private ReporteServices reporteServices;

    @Test
    void getPrestamosActivos_returnsActiveLoans() {
        Prestamo prestamo = crearPrestamo();
        when(prestamoRepository.buscarActivos(any(LocalDate.class))).thenReturn(List.of(prestamo));

        List<ReportePrestamo> resultado = reporteServices.getPrestamosActivos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("activo", resultado.get(0).getEstadoPrestamo());
    }

    @Test
    void getInventario_returnsInventorySummary() {
        Libro libro = crearLibro();
        when(libroRepository.findAllByOrderByTituloLibroAsc()).thenReturn(List.of(libro));

        List<ReporteInventario> resultado = reporteServices.getInventario();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).getTituloLibro());
    }

    private Prestamo crearPrestamo() {
        Persona personaUsuario = Persona.builder().idPersona(1).nombrePersona("Ana").apellidoPersona("Pérez").build();
        Persona personaBibliotecario = Persona.builder().idPersona(2).nombrePersona("Luis").apellidoPersona("Gómez").build();
        Usuario usuario = Usuario.builder().idUsuario(1).persona(personaUsuario).build();
        Bibliotecario bibliotecario = Bibliotecario.builder().idBibliotecario(1).persona(personaBibliotecario).build();

        Prestamo prestamo = Prestamo.builder()
                .idPrestamo(1)
                .estadoPrestamo("activo")
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucionPrevista(LocalDate.now().plusDays(3))
                .usuario(usuario)
                .bibliotecario(bibliotecario)
                .build();

        Ejemplar ejemplar = crearEjemplar();
        prestamo.setEjemplar(ejemplar);
        return prestamo;
    }

    private Ejemplar crearEjemplar() {
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIdEjemplar(1);
        ejemplar.setCodigoInventario("E-001");
        ejemplar.setDisponibilidadEjemplar("si");
        ejemplar.setLibro(crearLibroSinEjemplares());
        return ejemplar;
    }

    private Libro crearLibroSinEjemplares() {
        Categoria categoria = Categoria.builder().idCategoria(1).nombreCategoria("Tecnología").build();
        Editorial editorial = Editorial.builder().idEditorial(1).nombreEditorial("Planeta").build();
        Autor autor = Autor.builder().idAutor(1).nombreAutor("Gabriel").apellidoAutor("García").build();
        Libro libro = new Libro();
        libro.setIdLibro(1);
        libro.setTituloLibro("Java");
        libro.setIsbnLibro("123");
        libro.setCategoria(categoria);
        libro.setEditorial(editorial);
        libro.setAutores(List.of(autor));
        libro.setEjemplares(List.of());
        return libro;
    }

    private Libro crearLibro() {
        Libro libro = crearLibroSinEjemplares();
        Ejemplar ejemplar = crearEjemplar();
        ejemplar.setLibro(libro);
        libro.setEjemplares(List.of(ejemplar));
        return libro;
    }
}
