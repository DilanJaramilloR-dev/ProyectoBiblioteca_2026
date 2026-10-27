package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class EditorialDAOHelper {
    public String listarEditoriales() {
        return "Select idEditorial, correoEditorial, nombreEditorial, paisEditorial, telefonoEditorial from editorial";
    }

    public String obtenerEditorialPorId() {
        return "Select idEditorial, correoEditorial, nombreEditorial, paisEditorial, telefonoEditorial from editorial where idEditorial=?";
    }

    public String insertarEditorial() {
        return "Insert into editorial (correoEditorial, nombreEditorial, paisEditorial, telefonoEditorial) values (?,?,?,?)";
    }

    public String actualizarEditorial() {
        return "update editorial set correoEditorial=?, nombreEditorial=?, paisEditorial=?, telefonoEditorial=? where idEditorial=?";
    }

    public String eliminarEditorial() {
        return "delete from editorial where idEditorial=?";
    }
}
