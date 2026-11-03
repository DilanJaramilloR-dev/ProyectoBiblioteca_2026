package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Prestamo;
import edu.itm.proyecto2026.jpaEntities.Sancion;
import edu.itm.proyecto2026.jpaRepositories.PrestamoRepository;
import edu.itm.proyecto2026.jpaRepositories.SancionRepository;
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
class SancionServicesTest {

    @Mock
    private SancionRepository sancionRepository;

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private SancionServices sancionServices;

    @Test
    void getSanciones_returnsAllSanciones() {
        Sancion sancion = crearSancion();
        when(sancionRepository.findAll()).thenReturn(List.of(sancion));

        List<Sancion> resultado = sancionServices.getSanciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("multa", resultado.get(0).getTipoSancion());
    }

    @Test
    void insertarSancion_whenPrestamoExists_returnsSavedSancion() {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(1);
        Sancion sancion = crearSancion();
        sancion.setPrestamo(prestamo);

        when(prestamoRepository.findById(1)).thenReturn(Optional.of(prestamo));
        when(sancionRepository.save(any(Sancion.class))).thenReturn(sancion);

        Sancion resultado = sancionServices.insertarSancion(sancion);

        assertNotNull(resultado);
        assertEquals("multa", resultado.getTipoSancion());
    }

    @Test
    void actualizarSancion_whenNotExists_returnsNull() {
        Sancion sancion = crearSancion();
        sancion.setIdSancion(99);
        when(sancionRepository.findById(99)).thenReturn(Optional.empty());

        Sancion resultado = sancionServices.actualizarSancion(sancion);

        assertNull(resultado);
    }

    private Sancion crearSancion() {
        Sancion sancion = new Sancion();
        sancion.setIdSancion(1);
        sancion.setTipoSancion("multa");
        sancion.setMotivoSancion("Retraso");
        sancion.setFechaInicio(LocalDate.now());
        sancion.setFechaFin(LocalDate.now().plusDays(7));
        sancion.setEstadoSancion("activa");
        return sancion;
    }
}
