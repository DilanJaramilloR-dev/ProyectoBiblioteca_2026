package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Usuario;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements UsuarioRepositoryInterface {

    @Autowired
    private UsuarioDAOHelper helper;

    public List<Usuario> getUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarUsuarios());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return lista;
    }

    public Usuario getUsuario(Integer id) {
        Usuario usuario = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerUsuarioPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return usuario;
    }

    public Usuario insertarUsuario(Usuario usuario) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            if (usuario.getFechaRegistroUsuario() == null) {
                usuario.setFechaRegistroUsuario(LocalDate.now());
            }

            PreparedStatement psPersona = con.prepareStatement(helper.insertarPersona(), Statement.RETURN_GENERATED_KEYS);
            psPersona.setString(1, usuario.getNombrePersona());
            psPersona.setString(2, usuario.getApellidoPersona());
            psPersona.setString(3, usuario.getCorreoPersona());
            psPersona.setString(4, usuario.getTelefonoPersona());
            psPersona.executeUpdate();

            ResultSet keysPersona = psPersona.getGeneratedKeys();
            if (keysPersona.next()) {
                usuario.setIdPersona(keysPersona.getLong(1));
            }

            PreparedStatement psUsuario = con.prepareStatement(helper.insertarUsuario(), Statement.RETURN_GENERATED_KEYS);
            psUsuario.setLong(1, usuario.getIdPersona());
            psUsuario.setString(2, usuario.getDireccionUsuario());
            psUsuario.setDate(3, Date.valueOf(usuario.getFechaRegistroUsuario()));
            psUsuario.executeUpdate();

            ResultSet keysUsuario = psUsuario.getGeneratedKeys();
            if (keysUsuario.next()) {
                usuario.setIdUsuario(keysUsuario.getLong(1));
            }

            con.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
            usuario = null;
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return usuario;
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement psBuscar = con.prepareStatement(helper.consultarIdPersonaDeUsuario());
            psBuscar.setLong(1, usuario.getIdUsuario());
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                Long idPersona = rs.getLong("idPersona");

                PreparedStatement psPersona = con.prepareStatement(helper.actualizarPersona());
                psPersona.setString(1, usuario.getNombrePersona());
                psPersona.setString(2, usuario.getApellidoPersona());
                psPersona.setString(3, usuario.getCorreoPersona());
                psPersona.setString(4, usuario.getTelefonoPersona());
                psPersona.setLong(5, idPersona);
                psPersona.executeUpdate();

                PreparedStatement psUsuario = con.prepareStatement(helper.actualizarUsuario());
                psUsuario.setString(1, usuario.getDireccionUsuario());
                psUsuario.setLong(2, usuario.getIdUsuario());
                psUsuario.executeUpdate();

                con.commit();
                actualizado = true;
            } else {
                con.rollback();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return actualizado ? getUsuario(usuario.getIdUsuario().intValue()) : null;
    }

    public boolean eliminarUsuario(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement psBuscar = con.prepareStatement(helper.consultarIdPersonaDeUsuario());
            psBuscar.setInt(1, id);
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                PreparedStatement psEliminar = con.prepareStatement(helper.eliminarPersona());
                psEliminar.setLong(1, rs.getLong("idPersona"));
                int filas = psEliminar.executeUpdate();
                eliminado = filas > 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return eliminado;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return Usuario.builder()
                .idUsuario(rs.getLong("idUsuario"))
                .idPersona(rs.getLong("idPersona"))
                .nombrePersona(rs.getString("nombrePersona"))
                .apellidoPersona(rs.getString("apellidoPersona"))
                .correoPersona(rs.getString("correoPersona"))
                .telefonoPersona(rs.getString("telefonoPersona"))
                .direccionUsuario(rs.getString("direccionUsuario"))
                .fechaRegistroUsuario(rs.getDate("fechaRegistroUsuario").toLocalDate())
                .build();
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void restaurarAutoCommit(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
