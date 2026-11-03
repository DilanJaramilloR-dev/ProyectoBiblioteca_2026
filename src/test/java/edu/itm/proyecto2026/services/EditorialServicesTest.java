package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaRepositories.EditorialRepository;
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
class EditorialServicesTest {

    @Mock
    private EditorialRepository editorialRepository;

    @InjectMocks
    private EditorialServices editorialServices;

    @Test
    void getEditoriales_returnsAllEditoriales() {
        Editorial editorial = crearEditorial();
        when(editorialRepository.findAll()).thenReturn(List.of(editorial));

        List<Editorial> resultado = editorialServices.getEditoriales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Planeta", resultado.get(0).getNombreEditorial());
    }

    @Test
    void insertarEditorial_whenValid_returnsSavedEditorial() {
        Editorial editorial = crearEditorial();
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);

        Editorial resultado = editorialServices.insertarEditorial(editorial);

        assertNotNull(resultado);
        assertEquals("Planeta", resultado.getNombreEditorial());
    }

    @Test
    void getEditorial_whenNotExists_returnsNull() {
        when(editorialRepository.findById(99)).thenReturn(Optional.empty());

        Editorial resultado = editorialServices.getEditorial(99);

        assertNull(resultado);
    }

    @Test
    void actualizarEditorial_whenExists_returnsSavedEditorial() {
        Editorial editorial = crearEditorial();
        when(editorialRepository.existsById(1)).thenReturn(true);
        when(editorialRepository.save(any(Editorial.class))).thenReturn(editorial);

        Editorial resultado = editorialServices.actualizarEditorial(editorial);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEditorial());
    }

    private Editorial crearEditorial() {
        return Editorial.builder()
                .idEditorial(1)
                .nombreEditorial("Planeta")
                .correoEditorial("contacto@planeta.com")
                .paisEditorial("España")
                .telefonoEditorial("555111")
                .build();
    }
}
