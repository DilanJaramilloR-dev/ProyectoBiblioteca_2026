package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class LibroDAOHelper {

    /*
    Aclaracion:  Borren o pasen esta informacion a otro archivo si es necesario
    la columna del año en la BD se llama "a\u00f1opublicacionLibro" (con ñ).
    Se escribe con el escape unicode para que no dependa de la codificación del archivo,
     y se renombra con alias a anioPublicacionLibro para el resto del código.

     */

    public String listarLibros() {
        return "Select idLibro, idCategoria, idEditorial, tituloLibro, descripcionLibro, isbnLibro, " +
                "a\u00f1opublicacionLibro as anioPublicacionLibro from libro";
    }

    public String obtenerLibroPorId() {
        return "Select idLibro, idCategoria, idEditorial, tituloLibro, descripcionLibro, isbnLibro, " +
                "a\u00f1opublicacionLibro as anioPublicacionLibro from libro where idLibro=?";
    }

    public String insertarLibro() {
        return "Insert into libro (idCategoria, idEditorial, tituloLibro, descripcionLibro, isbnLibro, " +
                "a\u00f1opublicacionLibro) values (?,?,?,?,?,?)";
    }

    public String actualizarLibro() {
        return "update libro set idCategoria=?, idEditorial=?, tituloLibro=?, descripcionLibro=?, " +
                "isbnLibro=?, a\u00f1opublicacionLibro=? where idLibro=?";
    }

    public String eliminarLibro() {
        return "delete from libro where idLibro=?";
    }

    // Relación muchos a muchos Libro - Autor (tabla autor_libro)
    public String consultarAutoresDeLibro() {
        return "select idAutor from autor_libro where idLibro=?";
    }

    public String insertarAutorLibro() {
        return "Insert into autor_libro (idAutor, idLibro) values (?,?)";
    }

    public String eliminarAutoresDeLibro() {
        return "delete from autor_libro where idLibro=?";
    }
}
