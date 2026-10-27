package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Categoria;
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
public class CategoriaRepository implements CategoriaRepositoryInterface {

    @Autowired
    private CategoriaDAOHelper helper;

    public List<Categoria> getCategorias() {
        List<Categoria> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarCategorias());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCategoria(rs));
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

    public Categoria getCategoria(Integer id) {
        Categoria categoria = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerCategoriaPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categoria = mapearCategoria(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return categoria;
    }

    public Categoria insertarCategoria(Categoria categoria) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            // si no mandan estado, la categoría nace activa
            if (categoria.getEstadoCategoria() == null) {
                categoria.setEstadoCategoria("activa");
            }
            PreparedStatement ps = con.prepareStatement(helper.insertarCategoria(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoria.getDescripcionCategoria());
            ps.setString(2, categoria.getNombreCategoria());
            ps.setString(3, categoria.getEstadoCategoria());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                categoria.setIdCategoria(keys.getLong(1));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            categoria = null;
        } finally {
            cerrarConexion(con);
        }
        return categoria;
    }

    public Categoria actualizarCategoria(Categoria categoria) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarCategoria());
            ps.setString(1, categoria.getDescripcionCategoria());
            ps.setString(2, categoria.getNombreCategoria());
            ps.setString(3, categoria.getEstadoCategoria());
            ps.setLong(4, categoria.getIdCategoria());
            int filas = ps.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        // se vuelve a consultar para devolver lo que quedó realmente guardado
        return actualizado ? getCategoria(categoria.getIdCategoria().intValue()) : null;
    }

    public boolean eliminarCategoria(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarCategoria());
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

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return Categoria.builder()
                .idCategoria(rs.getLong("idCategoria"))
                .descripcionCategoria(rs.getString("descripcionCategoria"))
                .nombreCategoria(rs.getString("nombreCategoria"))
                .estadoCategoria(rs.getString("estadoCategoria"))
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
