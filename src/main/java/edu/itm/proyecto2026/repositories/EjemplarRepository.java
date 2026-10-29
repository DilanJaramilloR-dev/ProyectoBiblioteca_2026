package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Ejemplar;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EjemplarRepository implements EjemplarRepositoryInterface {

    @Autowired
    private EjemplarDAOHelper helper;

    public List<Ejemplar> getEjemplares() {
        List<Ejemplar> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarEjemplares());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearEjemplar(rs));
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

    public Ejemplar getEjemplar(Integer id) {
        Ejemplar ejemplar = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerEjemplarPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ejemplar = mapearEjemplar(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return ejemplar;
    }

    public Ejemplar insertarEjemplar(Ejemplar ejemplar) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.insertarEjemplar(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ejemplar.getIdLibro());
            ps.setString(2, ejemplar.getCodigoInventario());
            ps.setDate(3, Date.valueOf(ejemplar.getFechaAdquisicion()));
            ps.setString(4, ejemplar.getEstadoEjemplar());
            ps.setString(5, ejemplar.getUbicacionEjemplar());
            ps.setString(6, "si");
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                ejemplar.setIdEjemplar(keys.getLong(1));
            }
            ejemplar.setDisponibilidadEjemplar("si");
        } catch (Exception exception) {
            exception.printStackTrace();
            ejemplar = null;
        } finally {
            cerrarConexion(con);
        }
        return ejemplar;
    }

    public Ejemplar actualizarEjemplar(Ejemplar ejemplar) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarEjemplar());
            ps.setLong(1, ejemplar.getIdLibro());
            ps.setString(2, ejemplar.getCodigoInventario());
            ps.setDate(3, Date.valueOf(ejemplar.getFechaAdquisicion()));
            ps.setString(4, ejemplar.getEstadoEjemplar());
            ps.setString(5, ejemplar.getUbicacionEjemplar());
            ps.setLong(6, ejemplar.getIdEjemplar());
            int filas = ps.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return actualizado ? getEjemplar(ejemplar.getIdEjemplar().intValue()) : null;
    }

    public boolean eliminarEjemplar(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarEjemplar());
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

    private Ejemplar mapearEjemplar(ResultSet rs) throws SQLException {
        return Ejemplar.builder()
                .idEjemplar(rs.getLong("idEjemplar"))
                .idLibro(rs.getLong("idLibro"))
                .codigoInventario(rs.getString("codigoInventario"))
                .fechaAdquisicion(rs.getDate("fechaAdquisicion").toLocalDate())
                .estadoEjemplar(rs.getString("estadoEjemplar"))
                .ubicacionEjemplar(rs.getString("ubicacionEjemplar"))
                .disponibilidadEjemplar(rs.getString("disponibilidadEjemplar"))
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
