package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Editorial;
import edu.itm.proyecto2026.jpaRepositories.EditorialRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialServices implements EditorialServiceInterface {

    private final EditorialRepository editorialRepository;

    public EditorialServices(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public List<Editorial> getEditoriales() {
        return editorialRepository.findAll();
    }

    public Editorial getEditorial(Integer id) {
        return editorialRepository.findById(id).orElse(null);
    }

    public Editorial insertarEditorial(Editorial editorial) {
        try {
            editorial.setIdEditorial(null);
            return editorialRepository.save(editorial);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Editorial actualizarEditorial(Editorial editorial) {
        try {
            if (!editorialRepository.existsById(editorial.getIdEditorial())) {
                return null;
            }
            return editorialRepository.save(editorial);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean eliminarEditorial(Integer id) {
        try {
            if (!editorialRepository.existsById(id)) {
                return false;
            }
            editorialRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
