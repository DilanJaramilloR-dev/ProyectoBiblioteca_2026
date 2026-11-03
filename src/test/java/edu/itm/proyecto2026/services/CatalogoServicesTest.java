package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.CatalogoLibro;
import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.LibroRepository;
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
class CatalogoServicesTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private CatalogoServices catalogoServices;

    @Test
    void getCatalogo_returnsCatalogoList() {
        Libro libro = crearLibro();
        when(libroRepository.buscarCatalogo("", "", "")).thenReturn(List.of(libro));

        List<CatalogoLibro> resultado = catalogoServices.getCatalogo("", "", "");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).getTituloLibro());
    }

    @Test
    void getCatalogoLibro_whenExists_returnsCatalogoLibro() {
        Libro libro = crearLibro();
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

        CatalogoLibro resultado = catalogoServices.getCatalogoLibro(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdLibro());
    }

    private Libro crearLibro() {
        Categoria categoria = Categoria.builder().idCategoria(1).nombreCategoria("Tecnología").build();
        Editorial editorial = Editorial.builder().idEditorial(1).nombreEditorial("Planeta").build();
        Autor autor = Autor.builder().idAutor(1).nombreAutor("Gabriel").apellidoAutor("García").build();
        Ejemplar ejemplar = Ejemplar.builder().idEjemplar(1).codigoInventario("E-001").disponibilidadEjemplar("si").build();

        Libro libro = new Libro();
        libro.setIdLibro(1);
        libro.setTituloLibro("Java");
        libro.setIsbnLibro("123");
        libro.setDescripcionLibro("Libro de programación");
        libro.setAnioPublicacionLibro(2024);
        libro.setCategoria(categoria);
        libro.setEditorial(editorial);
        libro.setAutores(List.of(autor));
        libro.setEjemplares(List.of(ejemplar));
        return libro;
    }
}
