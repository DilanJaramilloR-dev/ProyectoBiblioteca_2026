package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Ejemplar;

import java.util.List;

public interface EjemplarServiceInterface {
    List<Ejemplar> getEjemplares();
    Ejemplar getEjemplar(Integer id);
    Ejemplar insertarEjemplar(Ejemplar ejemplar);
    Ejemplar actualizarEjemplar(Ejemplar ejemplar);
    boolean eliminarEjemplar(Integer id);
}
