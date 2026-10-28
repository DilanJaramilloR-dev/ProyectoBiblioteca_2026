package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Ejemplar;

import java.util.List;

public interface EjemplarRepositoryInterface {
    List<Ejemplar> getEjemplares();
    Ejemplar getEjemplar(Integer id);
    Ejemplar insertarEjemplar(Ejemplar ejemplar);
    Ejemplar actualizarEjemplar(Ejemplar ejemplar);
    boolean eliminarEjemplar(Integer id);
}
