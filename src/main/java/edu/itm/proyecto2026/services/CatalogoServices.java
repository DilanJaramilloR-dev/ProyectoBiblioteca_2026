package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.CatalogoLibro;
import edu.itm.proyecto2026.repositories.CatalogoRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoServices implements CatalogoServiceInterface {

    private final CatalogoRepositoryInterface catalogoRepository;

    public CatalogoServices(CatalogoRepositoryInterface catalogoRepository) {
        this.catalogoRepository = catalogoRepository;
    }

    public List<CatalogoLibro> getCatalogo(String titulo, String autor, String categoria) {
        return catalogoRepository.getCatalogo(titulo, autor, categoria);
    }

    public CatalogoLibro getCatalogoLibro(Integer id) {
        return catalogoRepository.getCatalogoLibro(id);
    }
}