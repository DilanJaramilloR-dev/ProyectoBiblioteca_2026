package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Bibliotecario;
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
public class BibliotecarioRepository implements BibliotecarioRepositoryInterface {

    @Autowired
    private BibliotecarioDAOHelper helper;

    public List<Bibliotecario> getBibliotecarios() {
        List<Bibliotecario> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarBibliotecarios());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearBibliotecario(rs));
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

    public Bibliotecario getBibliotecario(Integer id) {
        Bibliotecario bibliotecario = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerBibliotecarioPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bibliotecario = mapearBibliotecario(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return bibliotecario;
    }

    public Bibliotecario insertarBibliotecario(Bibliotecario bibliotecario) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement psPersona = con.prepareStatement(helper.insertarPersona(), Statement.RETURN_GENERATED_KEYS);
            psPersona.setString(1, bibliotecario.getNombrePersona());
            psPersona.setString(2, bibliotecario.getApellidoPersona());
            psPersona.setString(3, bibliotecario.getCorreoPersona());
            psPersona.setString(4, bibliotecario.getTelefonoPersona());
            psPersona.executeUpdate();

            ResultSet keysPersona = psPersona.getGeneratedKeys();
            if (keysPersona.next()) {
                bibliotecario.setIdPersona(keysPersona.getLong(1));
            }

            PreparedStatement psBibliotecario = con.prepareStatement(helper.insertarBibliotecario(), Statement.RETURN_GENERATED_KEYS);
            psBibliotecario.setLong(1, bibliotecario.getIdPersona());
            psBibliotecario.setString(2, bibliotecario.getUsuarioLoginBibliotecario());
            psBibliotecario.setString(3, bibliotecario.getContrasenaBibliotecario());
            psBibliotecario.setString(4, bibliotecario.getDocumentoBibliotecario());
            psBibliotecario.executeUpdate();

            ResultSet keysBibliotecario = psBibliotecario.getGeneratedKeys();
            if (keysBibliotecario.next()) {
                bibliotecario.setIdBibliotecario(keysBibliotecario.getLong(1));
            }

            con.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
            bibliotecario = null;
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return bibliotecario;
    }

    public Bibliotecario actualizarBibliotecario(Bibliotecario bibliotecario) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement psBuscar = con.prepareStatement(helper.consultarIdPersonaDeBibliotecario());
            psBuscar.setLong(1, bibliotecario.getIdBibliotecario());
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                Long idPersona = rs.getLong("idPersona");

                PreparedStatement psPersona = con.prepareStatement(helper.actualizarPersona());
                psPersona.setString(1, bibliotecario.getNombrePersona());
                psPersona.setString(2, bibliotecario.getApellidoPersona());
                psPersona.setString(3, bibliotecario.getCorreoPersona());
                psPersona.setString(4, bibliotecario.getTelefonoPersona());
                psPersona.setLong(5, idPersona);
                psPersona.executeUpdate();

                PreparedStatement psBibliotecario = con.prepareStatement(helper.actualizarBibliotecario());
                psBibliotecario.setString(1, bibliotecario.getUsuarioLoginBibliotecario());
                psBibliotecario.setString(2, bibliotecario.getDocumentoBibliotecario());
                psBibliotecario.setLong(3, bibliotecario.getIdBibliotecario());
                psBibliotecario.executeUpdate();

                if (bibliotecario.getContrasenaBibliotecario() != null
                        && !bibliotecario.getContrasenaBibliotecario().isBlank()) {
                    PreparedStatement psContrasena = con.prepareStatement(helper.actualizarContrasena());
                    psContrasena.setString(1, bibliotecario.getContrasenaBibliotecario());
                    psContrasena.setLong(2, bibliotecario.getIdBibliotecario());
                    psContrasena.executeUpdate();
                }

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
        return actualizado ? getBibliotecario(bibliotecario.getIdBibliotecario().intValue()) : null;
    }

    public boolean eliminarBibliotecario(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement psBuscar = con.prepareStatement(helper.consultarIdPersonaDeBibliotecario());
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

    private Bibliotecario mapearBibliotecario(ResultSet rs) throws SQLException {
        return Bibliotecario.builder()
                .idBibliotecario(rs.getLong("idBibliotecario"))
                .idPersona(rs.getLong("idPersona"))
                .nombrePersona(rs.getString("nombrePersona"))
                .apellidoPersona(rs.getString("apellidoPersona"))
                .correoPersona(rs.getString("correoPersona"))
                .telefonoPersona(rs.getString("telefonoPersona"))
                .usuarioLoginBibliotecario(rs.getString("usuarioLoginBibliotecario"))
                .documentoBibliotecario(rs.getString("documentoBibliotecario"))
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
