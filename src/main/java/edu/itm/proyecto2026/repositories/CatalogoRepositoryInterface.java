package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.CatalogoLibro;

import java.util.List;

public interface CatalogoRepositoryInterface {
    List<CatalogoLibro> getCatalogo(String titulo, String autor, String categoria);
    CatalogoLibro getCatalogoLibro(Integer id);
}