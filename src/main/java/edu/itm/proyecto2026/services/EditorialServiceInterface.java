package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Editorial;

import java.util.List;

public interface EditorialServiceInterface {
    List<Editorial> getEditoriales();
    Editorial getEditorial(Integer id);
    Editorial insertarEditorial(Editorial editorial);
    Editorial actualizarEditorial(Editorial editorial);
    boolean eliminarEditorial(Integer id);
}
