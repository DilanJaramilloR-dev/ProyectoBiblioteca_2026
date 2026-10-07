package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Prestamo;

import java.util.List;

public interface PrestamoServiceInterface {
    List<Prestamo> getPrestamos();
    Prestamo getPrestamo(Integer id);
    Prestamo insertarPrestamo(Prestamo prestamo);
    Prestamo devolverPrestamo(Integer id);
    Prestamo actualizarPrestamo(Prestamo prestamo);
    boolean eliminarPrestamo(Integer id);
}
