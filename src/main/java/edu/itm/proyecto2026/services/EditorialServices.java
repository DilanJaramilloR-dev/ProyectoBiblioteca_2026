package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Editorial;
import edu.itm.proyecto2026.repositories.EditorialRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialServices implements EditorialServiceInterface {

    private final EditorialRepositoryInterface editorialRepository;

    public EditorialServices(EditorialRepositoryInterface editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public List<Editorial> getEditoriales() {
        return editorialRepository.getEditoriales();
    }

    public Editorial getEditorial(Integer id) {
        return editorialRepository.getEditorial(id);
    }

    public Editorial insertarEditorial(Editorial editorial) {
        return editorialRepository.insertarEditorial(editorial);
    }

    public Editorial actualizarEditorial(Editorial editorial) {
        return editorialRepository.actualizarEditorial(editorial);
    }

    public boolean eliminarEditorial(Integer id) {
        return editorialRepository.eliminarEditorial(id);
    }
}
