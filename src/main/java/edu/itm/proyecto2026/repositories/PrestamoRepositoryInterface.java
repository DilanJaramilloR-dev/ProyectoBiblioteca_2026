package edu.itm.proyecto2026.repositories;
import edu.itm.proyecto2026.identities.Prestamo;

import java.util.List;
public interface PrestamoRepositoryInterface {
    List<Prestamo> getPrestamos();
    Prestamo getPrestamo(Integer id);
    Prestamo insertarPrestamo(Prestamo prestamo);
    Prestamo devolverPrestamo(Integer idPrestamo);
    Prestamo actualizarPrestamo(Prestamo prestamo);
    boolean eliminarPrestamo(Integer id);
}
