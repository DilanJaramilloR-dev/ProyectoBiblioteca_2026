package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class BibliotecarioDAOHelper {

    public String listarBibliotecarios() {
        return "Select b.idBibliotecario, b.idPersona, p.nombrePersona, p.apellidoPersona, p.correoPersona, " +
                "p.telefonoPersona, b.usuarioLoginBibliotecario, b.documentoBibliotecario " +
                "from bibliotecario b join persona p on p.idPersona = b.idPersona";
    }

    public String obtenerBibliotecarioPorId() {
        return "Select b.idBibliotecario, b.idPersona, p.nombrePersona, p.apellidoPersona, p.correoPersona, " +
                "p.telefonoPersona, b.usuarioLoginBibliotecario, b.documentoBibliotecario " +
                "from bibliotecario b join persona p on p.idPersona = b.idPersona where b.idBibliotecario=?";
    }

    public String insertarPersona() {
        return "Insert into persona (nombrePersona, apellidoPersona, correoPersona, telefonoPersona) values (?,?,?,?)";
    }

    public String insertarBibliotecario() {
        return "Insert into bibliotecario (idPersona, usuarioLoginBibliotecario, contrasenaBibliotecario, documentoBibliotecario) values (?,?,?,?)";
    }

    public String consultarIdPersonaDeBibliotecario() {
        return "Select idPersona from bibliotecario where idBibliotecario=?";
    }

    public String actualizarPersona() {
        return "update persona set nombrePersona=?, apellidoPersona=?, correoPersona=?, telefonoPersona=? where idPersona=?";
    }

    public String actualizarBibliotecario() {
        return "update bibliotecario set usuarioLoginBibliotecario=?, documentoBibliotecario=? where idBibliotecario=?";
    }

    public String actualizarContrasena() {
        return "update bibliotecario set contrasenaBibliotecario=? where idBibliotecario=?";
    }

    public String eliminarPersona() {
        return "delete from persona where idPersona=?";
    }
}
