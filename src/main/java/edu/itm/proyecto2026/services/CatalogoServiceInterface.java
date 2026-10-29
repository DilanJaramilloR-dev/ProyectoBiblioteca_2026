package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.CatalogoLibro;

import java.util.List;

public interface CatalogoServiceInterface {
    List<CatalogoLibro> getCatalogo(String titulo, String autor, String categoria);
    CatalogoLibro getCatalogoLibro(Integer id);
}