package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.CatalogoLibro;
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
public class CatalogoRepository implements CatalogoRepositoryInterface {

    @Autowired
    private CatalogoDAOHelper helper;

    public List<CatalogoLibro> getCatalogo(String titulo, String autor, String categoria) {
        List<CatalogoLibro> lista = new ArrayList<>();
        String filtroTitulo = normalizar(titulo);
        String filtroAutor = normalizar(autor);
        String filtroCategoria = normalizar(categoria);

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarCatalogo());
            ps.setString(1, filtroTitulo);
            ps.setString(2, filtroTitulo);
            ps.setString(3, filtroCategoria);
            ps.setString(4, filtroCategoria);
            ps.setString(5, filtroAutor);
            ps.setString(6, filtroAutor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCatalogoLibro(rs));
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

    public CatalogoLibro getCatalogoLibro(Integer id) {
        CatalogoLibro libro = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.consultarCatalogoPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                libro = mapearCatalogoLibro(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return libro;
    }

    private String normalizar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }

    private CatalogoLibro mapearCatalogoLibro(ResultSet rs) throws SQLException {
        return CatalogoLibro.builder()
                .idLibro(rs.getLong("idLibro"))
                .tituloLibro(rs.getString("tituloLibro"))
                .isbnLibro(rs.getString("isbnLibro"))
                .descripcionLibro(rs.getString("descripcionLibro"))
                .anioPublicacionLibro(rs.getInt("anioPublicacionLibro"))
                .nombreCategoria(rs.getString("nombreCategoria"))
                .nombreEditorial(rs.getString("nombreEditorial"))
                .autores(rs.getString("autores"))
                .ejemplaresDisponibles(rs.getInt("ejemplaresDisponibles"))
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