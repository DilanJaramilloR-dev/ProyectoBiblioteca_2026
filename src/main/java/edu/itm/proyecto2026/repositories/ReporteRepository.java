package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.ReporteInventario;
import edu.itm.proyecto2026.identities.ReportePrestamo;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReporteRepository implements ReporteRepositoryInterface {

    @Autowired
    private ReporteDAOHelper helper;

    public List<ReportePrestamo> getPrestamosActivos() {
        return consultarPrestamos(helper.listarPrestamosActivos());
    }

    public List<ReportePrestamo> getPrestamosVencidos() {
        return consultarPrestamos(helper.listarPrestamosVencidos());
    }

    public List<ReporteInventario> getInventario() {
        List<ReporteInventario> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarInventario());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReporteInventario(rs));
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

    private List<ReportePrestamo> consultarPrestamos(String sql) {
        List<ReportePrestamo> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReportePrestamo(rs));
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

    private ReportePrestamo mapearReportePrestamo(ResultSet rs) throws SQLException {
        return ReportePrestamo.builder()
                .idPrestamo(rs.getLong("idPrestamo"))
                .codigoInventario(rs.getString("codigoInventario"))
                .tituloLibro(rs.getString("tituloLibro"))
                .nombreUsuario(rs.getString("nombreUsuario"))
                .nombreBibliotecario(rs.getString("nombreBibliotecario"))
                .estadoPrestamo(rs.getString("estadoPrestamo"))
                .fechaPrestamo(rs.getDate("fechaPrestamo").toLocalDate())
                .fechaDevolucionPrevista(rs.getDate("fechaDevolucionPrevista").toLocalDate())
                .diasRetraso(rs.getInt("diasRetraso"))
                .build();
    }

    private ReporteInventario mapearReporteInventario(ResultSet rs) throws SQLException {
        int total = rs.getInt("totalEjemplares");
        int disponibles = rs.getInt("ejemplaresDisponibles");
        return ReporteInventario.builder()
                .idLibro(rs.getLong("idLibro"))
                .tituloLibro(rs.getString("tituloLibro"))
                .isbnLibro(rs.getString("isbnLibro"))
                .nombreCategoria(rs.getString("nombreCategoria"))
                .totalEjemplares(total)
                .ejemplaresDisponibles(disponibles)
                .ejemplaresNoDisponibles(total - disponibles)
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