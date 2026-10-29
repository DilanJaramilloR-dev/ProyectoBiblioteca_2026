package edu.itm.proyecto2026.repositories;


import edu.itm.proyecto2026.identities.Sancion;

import java.util.List;

public interface SancionRepositoryInterface {
    List<Sancion> getSanciones();
    Sancion getSancion(Integer id);
    Sancion insertarSancion(Sancion sancion);
    Sancion actualizarSancion(Sancion sancion);
}
