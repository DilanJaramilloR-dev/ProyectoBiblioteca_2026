package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.jpaEntities.Autor;
import edu.itm.proyecto2026.jpaRepositories.AutorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServices implements AutorServiceInterface {

    private final AutorRepository autorRepository;

    public AutorServices(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAutor() {
        return autorRepository.findAll();
    }

    public Autor insertarAutor(Autor autor) {
        try {
            autor.setIdAutor(null);
            return autorRepository.save(autor);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Autor actualizarAutor(Autor autor) {
        try {
            if (!autorRepository.existsById(autor.getIdAutor())) {
                return null;
            }
            return autorRepository.save(autor);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Autor getAutor(Integer id) {
        return autorRepository.findById(id).orElse(null);
    }

    public boolean eliminarAutor(Integer id) {
        try {
            if (!autorRepository.existsById(id)) {
                return false;
            }
            autorRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}