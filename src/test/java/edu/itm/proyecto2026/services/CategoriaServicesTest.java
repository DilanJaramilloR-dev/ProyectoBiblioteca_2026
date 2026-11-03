package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Categoria;
import edu.itm.proyecto2026.jpaRepositories.CategoriaRepository;
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
class CategoriaServicesTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServices categoriaServices;

    @Test
    void getCategorias_returnsAllCategorias() {
        Categoria categoria = crearCategoria();
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> resultado = categoriaServices.getCategorias();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tecnología", resultado.get(0).getNombreCategoria());
    }

    @Test
    void insertarCategoria_setsDefaultStateAndSaves() {
        Categoria categoria = crearCategoria();
        categoria.setEstadoCategoria(null);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = categoriaServices.insertarCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("activa", resultado.getEstadoCategoria());
    }

    @Test
    void actualizarCategoria_whenNotExists_returnsNull() {
        Categoria categoria = Categoria.builder().idCategoria(99).nombreCategoria("Tecnología").build();
        when(categoriaRepository.existsById(99)).thenReturn(false);

        Categoria resultado = categoriaServices.actualizarCategoria(categoria);

        assertNull(resultado);
    }

    @Test
    void eliminarCategoria_whenExists_returnsTrue() {
        when(categoriaRepository.existsById(1)).thenReturn(true);

        boolean resultado = categoriaServices.eliminarCategoria(1);

        assertTrue(resultado);
        verify(categoriaRepository).deleteById(1);
    }

    private Categoria crearCategoria() {
        return Categoria.builder()
                .idCategoria(1)
                .nombreCategoria("Tecnología")
                .descripcionCategoria("Libros de tecnología")
                .estadoCategoria("activa")
                .build();
    }
}
