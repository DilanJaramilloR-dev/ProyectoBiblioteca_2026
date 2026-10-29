package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class SancionDAOHelper {

    public String listarSanciones() {
        return "Select idSancion, idPrestamo, tipoSancion, motivoSancion, fechaInicio, fechaFin, estadoSancion from sancion";
    }

    public String obtenerSancionPorId() {
        return "Select idSancion, idPrestamo, tipoSancion, motivoSancion, fechaInicio, fechaFin, estadoSancion from sancion where idSancion=?";
    }

    public String insertarSancion() {
        return "Insert into sancion (idPrestamo, tipoSancion, motivoSancion, fechaInicio, fechaFin, estadoSancion) values (?,?,?,?,?,?)";
    }

    public String actualizarSancion() {
        return "update sancion set tipoSancion=?, motivoSancion=?, fechaFin=?, estadoSancion=? where idSancion=?";
    }
}