package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Editorial;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EditorialRepository implements EditorialRepositoryInterface {

    @Autowired
    private EditorialDAOHelper helper;

    public List<Editorial> getEditoriales() {
        List<Editorial> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarEditoriales());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearEditorial(rs));
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

    public Editorial getEditorial(Integer id) {
        Editorial editorial = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerEditorialPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                editorial = mapearEditorial(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return editorial;
    }

    public Editorial insertarEditorial(Editorial editorial) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.insertarEditorial(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, editorial.getCorreoEditorial());
            ps.setString(2, editorial.getNombreEditorial());
            ps.setString(3, editorial.getPaisEditorial());
            ps.setString(4, editorial.getTelefonoEditorial());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                editorial.setIdEditorial(keys.getLong(1));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            editorial = null;
        } finally {
            cerrarConexion(con);
        }
        return editorial;
    }

    public Editorial actualizarEditorial(Editorial editorial) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarEditorial());
            ps.setString(1, editorial.getCorreoEditorial());
            ps.setString(2, editorial.getNombreEditorial());
            ps.setString(3, editorial.getPaisEditorial());
            ps.setString(4, editorial.getTelefonoEditorial());
            ps.setLong(5, editorial.getIdEditorial());
            int filas = ps.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }

        // se vuelve a consultar para devolver el valor realmente guardado
        return actualizado ? getEditorial(editorial.getIdEditorial().intValue()) : null;
    }

    public boolean eliminarEditorial(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarEditorial());
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            eliminado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return eliminado;
    }

    private Editorial mapearEditorial(ResultSet rs) throws SQLException {
        return Editorial.builder()
                .idEditorial(rs.getLong("idEditorial"))
                .correoEditorial(rs.getString("correoEditorial"))
                .nombreEditorial(rs.getString("nombreEditorial"))
                .paisEditorial(rs.getString("paisEditorial"))
                .telefonoEditorial(rs.getString("telefonoEditorial"))
                .build();
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