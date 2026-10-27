package edu.itm.proyecto2026.repositories;


import org.springframework.stereotype.Component;

@Component
public class CategoriaDAOHelper {
    public String listarCategorias() {
        return "Select idCategoria, descripcionCategoria, nombreCategoria, estadoCategoria from categoria";
    }

    public String obtenerCategoriaPorId() {
        return "Select idCategoria, descripcionCategoria, nombreCategoria, estadoCategoria from categoria where idCategoria=?";
    }

    public String insertarCategoria() {
        return "Insert into categoria (descripcionCategoria, nombreCategoria, estadoCategoria) values (?,?,?)";
    }

    public String actualizarCategoria() {
        return "update categoria set descripcionCategoria=?, nombreCategoria=?, estadoCategoria=? where idCategoria=?";
    }

    public String eliminarCategoria() {
        return "delete from categoria where idCategoria=?";
    }
}
