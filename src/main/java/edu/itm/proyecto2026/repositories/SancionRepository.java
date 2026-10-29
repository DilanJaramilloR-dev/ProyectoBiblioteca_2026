package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Sancion;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SancionRepository implements SancionRepositoryInterface {

    @Autowired
    private SancionDAOHelper helper;

    public List<Sancion> getSanciones() {
        List<Sancion> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarSanciones());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearSancion(rs));
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

    public Sancion getSancion(Integer id) {
        Sancion sancion = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerSancionPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sancion = mapearSancion(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return sancion;
    }

    public Sancion insertarSancion(Sancion sancion) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            if (sancion.getFechaInicio() == null) {
                sancion.setFechaInicio(LocalDate.now());
            }
            if (sancion.getEstadoSancion() == null) {
                sancion.setEstadoSancion("activa");
            }

            PreparedStatement ps = con.prepareStatement(helper.insertarSancion(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, sancion.getIdPrestamo());
            ps.setString(2, sancion.getTipoSancion());
            ps.setString(3, sancion.getMotivoSancion());
            ps.setDate(4, Date.valueOf(sancion.getFechaInicio()));
            if (sancion.getFechaFin() != null) {
                ps.setDate(5, Date.valueOf(sancion.getFechaFin()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, sancion.getEstadoSancion());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                sancion.setIdSancion(keys.getLong(1));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            sancion = null;
        } finally {
            cerrarConexion(con);
        }
        return sancion;
    }

    public Sancion actualizarSancion(Sancion sancion) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarSancion());
            ps.setString(1, sancion.getTipoSancion());
            ps.setString(2, sancion.getMotivoSancion());
            if (sancion.getFechaFin() != null) {
                ps.setDate(3, Date.valueOf(sancion.getFechaFin()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, sancion.getEstadoSancion());
            ps.setLong(5, sancion.getIdSancion());
            int filas = ps.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return actualizado ? getSancion(sancion.getIdSancion().intValue()) : null;
    }

    private Sancion mapearSancion(ResultSet rs) throws SQLException {
        Date fechaFin = rs.getDate("fechaFin");
        return Sancion.builder()
                .idSancion(rs.getLong("idSancion"))
                .idPrestamo(rs.getLong("idPrestamo"))
                .tipoSancion(rs.getString("tipoSancion"))
                .motivoSancion(rs.getString("motivoSancion"))
                .fechaInicio(rs.getDate("fechaInicio").toLocalDate())
                .fechaFin(fechaFin != null ? fechaFin.toLocalDate() : null)
                .estadoSancion(rs.getString("estadoSancion"))
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