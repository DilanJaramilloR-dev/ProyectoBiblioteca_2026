package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class UsuarioDAOHelper {

    public String listarUsuarios() {
        return "Select u.idUsuario, u.idPersona, p.nombrePersona, p.apellidoPersona, p.correoPersona, " +
                "p.telefonoPersona, u.direccionUsuario, u.fechaRegistroUsuario " +
                "from usuario u join persona p on p.idPersona = u.idPersona";
    }

    public String obtenerUsuarioPorId() {
        return "Select u.idUsuario, u.idPersona, p.nombrePersona, p.apellidoPersona, p.correoPersona, " +
                "p.telefonoPersona, u.direccionUsuario, u.fechaRegistroUsuario " +
                "from usuario u join persona p on p.idPersona = u.idPersona where u.idUsuario=?";
    }

    public String insertarPersona() {
        return "Insert into persona (nombrePersona, apellidoPersona, correoPersona, telefonoPersona) values (?,?,?,?)";
    }

    public String insertarUsuario() {
        return "Insert into usuario (idPersona, direccionUsuario, fechaRegistroUsuario) values (?,?,?)";
    }

    public String consultarIdPersonaDeUsuario() {
        return "Select idPersona from usuario where idUsuario=?";
    }

    public String actualizarPersona() {
        return "update persona set nombrePersona=?, apellidoPersona=?, correoPersona=?, telefonoPersona=? where idPersona=?";
    }

    public String actualizarUsuario() {
        return "update usuario set direccionUsuario=? where idUsuario=?";
    }

    public String eliminarPersona() {
        return "delete from persona where idPersona=?";
    }
}
