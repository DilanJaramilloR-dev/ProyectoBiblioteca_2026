package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaEntities.Libro;
import edu.itm.proyecto2026.jpaRepositories.AutorRepository;
import edu.itm.proyecto2026.jpaRepositories.CategoriaRepository;
import edu.itm.proyecto2026.jpaRepositories.EditorialRepository;
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
class LibroServicesTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private EditorialRepository editorialRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServices libroServices;

    @Test
    void getLibros_returnsAllLibros() {
        Libro libro = crearLibro();
        when(libroRepository.findAll()).thenReturn(List.of(libro));

        List<Libro> resultado = libroServices.getLibros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).getTituloLibro());
    }

    @Test
    void insertarLibro_whenCategoriaAndEditorialAndAutoresExist_returnsSavedLibro() {
        Libro libro = crearLibro();
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(libro.getCategoria()));
        when(editorialRepository.findById(1)).thenReturn(Optional.of(libro.getEditorial()));
        when(autorRepository.findById(1)).thenReturn(Optional.of(libro.getAutores().get(0)));
        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        Libro resultado = libroServices.insertarLibro(libro);

        assertNotNull(resultado);
        assertEquals("Java", resultado.getTituloLibro());
    }

    @Test
    void actualizarLibro_whenNotExists_returnsNull() {
        Libro libro = crearLibro();
        libro.setIdLibro(99);
        when(libroRepository.existsById(99)).thenReturn(false);

        Libro resultado = libroServices.actualizarLibro(libro);

        assertNull(resultado);
    }

    @Test
    void eliminarLibro_whenExists_returnsTrue() {
        when(libroRepository.existsById(1)).thenReturn(true);

        boolean resultado = libroServices.eliminarLibro(1);

        assertTrue(resultado);
        verify(libroRepository).deleteById(1);
    }

    private Libro crearLibro() {
        Categoria categoria = Categoria.builder().idCategoria(1).nombreCategoria("Tecnología").build();
        Editorial editorial = Editorial.builder().idEditorial(1).nombreEditorial("Planeta").build();
        Autor autor = Autor.builder().idAutor(1).nombreAutor("Gabriel").apellidoAutor("García").build();
        Libro libro = new Libro();
        libro.setIdLibro(1);
        libro.setCategoria(categoria);
        libro.setEditorial(editorial);
        libro.setTituloLibro("Java");
        libro.setDescripcionLibro("Libro de Java");
        libro.setIsbnLibro("123456");
        libro.setAnioPublicacionLibro(2024);
        libro.setAutores(List.of(autor));
        return libro;
    }
}
