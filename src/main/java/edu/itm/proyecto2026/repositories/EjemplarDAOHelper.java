package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class EjemplarDAOHelper {

    public String listarEjemplares() {
        return "Select idEjemplar, idLibro, codigoInventario, fechaAdquisicion, estadoEjemplar, ubicacionEjemplar, disponibilidadEjemplar from ejemplar";
    }

    public String obtenerEjemplarPorId() {
        return "Select idEjemplar, idLibro, codigoInventario, fechaAdquisicion, estadoEjemplar, ubicacionEjemplar, disponibilidadEjemplar from ejemplar where idEjemplar=?";
    }

    public String insertarEjemplar() {
        return "Insert into ejemplar (idLibro, codigoInventario, fechaAdquisicion, estadoEjemplar, ubicacionEjemplar, disponibilidadEjemplar) values (?,?,?,?,?,?)";
    }

    public String actualizarEjemplar() {
        return "update ejemplar set idLibro=?, codigoInventario=?, fechaAdquisicion=?, estadoEjemplar=?, ubicacionEjemplar=? where idEjemplar=?";
    }

    public String eliminarEjemplar() {
        return "delete from ejemplar where idEjemplar=?";
    }
}
