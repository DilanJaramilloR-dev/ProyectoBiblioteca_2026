package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Sancion;

import java.util.List;

public interface SancionServiceInterface {
    List<Sancion> getSanciones();
    Sancion getSancion(Integer id);
    Sancion insertarSancion(Sancion sancion);
    Sancion actualizarSancion(Sancion sancion);
}