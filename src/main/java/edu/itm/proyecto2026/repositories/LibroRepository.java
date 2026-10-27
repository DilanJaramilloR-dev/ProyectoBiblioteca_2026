package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Libro;
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
public class LibroRepository implements LibroRepositoryInterface {

    @Autowired
    private LibroDAOHelper helper;

    public List<Libro> getLibros() {
        List<Libro> libros = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarLibros());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = mapearLibro(rs);
                libro.setIdsAutores(obtenerIdsAutores(con, libro.getIdLibro()));
                libros.add(libro);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return libros;
    }

    public Libro getLibro(Integer id) {
        Libro libro = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerLibroPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                libro = mapearLibro(rs);
                libro.setIdsAutores(obtenerIdsAutores(con, libro.getIdLibro()));
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


    public Libro insertarLibro(Libro libro) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(helper.insertarLibro(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, libro.getIdCategoria());
            ps.setLong(2, libro.getIdEditorial());
            ps.setString(3, libro.getTituloLibro());
            ps.setString(4, libro.getDescripcionLibro());
            ps.setString(5, libro.getIsbnLibro());
            ps.setInt(6, libro.getAnioPublicacionLibro());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                libro.setIdLibro(keys.getLong(1));
            }

            insertarAutores(con, libro.getIdLibro(), libro.getIdsAutores());

            con.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
            libro = null;
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return libro;
    }

    /**
     * Actualiza el libro y reemplaza sus autores (borra los de autor_libro y vuelve a insertar
     * los que llegan en idsAutores)
     * (todo en una sola transacción)
     */
    public Libro actualizarLibro(Libro libro) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(helper.actualizarLibro());
            ps.setLong(1, libro.getIdCategoria());
            ps.setLong(2, libro.getIdEditorial());
            ps.setString(3, libro.getTituloLibro());
            ps.setString(4, libro.getDescripcionLibro());
            ps.setString(5, libro.getIsbnLibro());
            ps.setInt(6, libro.getAnioPublicacionLibro());
            ps.setLong(7, libro.getIdLibro());
            int filas = ps.executeUpdate();

            if (filas == 0) {
                con.rollback();
            } else {
                PreparedStatement borrar = con.prepareStatement(helper.eliminarAutoresDeLibro());
                borrar.setLong(1, libro.getIdLibro());
                borrar.executeUpdate();

                insertarAutores(con, libro.getIdLibro(), libro.getIdsAutores());

                con.commit();
                actualizado = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }

        return actualizado ? getLibro(libro.getIdLibro().intValue()) : null;
    }

    public boolean eliminarLibro(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarLibro());
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

    private List<Long> obtenerIdsAutores(Connection con, Long idLibro) throws SQLException {
        List<Long> ids = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(helper.consultarAutoresDeLibro());
        ps.setLong(1, idLibro);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ids.add(rs.getLong("idAutor"));
        }
        return ids;
    }

    private void insertarAutores(Connection con, Long idLibro, List<Long> idsAutores) throws SQLException {
        for (Long idAutor : idsAutores) {
            PreparedStatement ps = con.prepareStatement(helper.insertarAutorLibro());
            ps.setLong(1, idAutor);
            ps.setLong(2, idLibro);
            ps.executeUpdate();
        }
    }

    private Libro mapearLibro(ResultSet rs) throws SQLException {
        return Libro.builder()
                .idLibro(rs.getLong("idLibro"))
                .idCategoria(rs.getLong("idCategoria"))
                .idEditorial(rs.getLong("idEditorial"))
                .tituloLibro(rs.getString("tituloLibro"))
                .descripcionLibro(rs.getString("descripcionLibro"))
                .isbnLibro(rs.getString("isbnLibro"))
                .anioPublicacionLibro(rs.getInt("anioPublicacionLibro"))
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