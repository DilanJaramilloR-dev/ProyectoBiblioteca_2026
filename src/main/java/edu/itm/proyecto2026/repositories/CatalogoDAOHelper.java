package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class CatalogoDAOHelper {

    private static final String SELECT_CATALOGO =
            "Select l.idLibro, l.tituloLibro, l.isbnLibro, l.descripcionLibro, " +
                    "l.a\u00f1opublicacionLibro as anioPublicacionLibro, c.nombreCategoria, e.nombreEditorial, " +
                    "(Select group_concat(concat(a.nombreAutor, ' ', a.apellidoAutor) separator ', ') " +
                    "from autor_libro al join autor a on a.idAutor = al.idAutor where al.idLibro = l.idLibro) as autores, " +
                    "(Select count(*) from ejemplar ej where ej.idLibro = l.idLibro and ej.disponibilidadEjemplar = 'si') as ejemplaresDisponibles " +
                    "from libro l " +
                    "join categoria c on c.idCategoria = l.idCategoria " +
                    "join editorial e on e.idEditorial = l.idEditorial ";

    public String listarCatalogo() {
        return SELECT_CATALOGO +
                "where exists (Select 1 from ejemplar ex where ex.idLibro = l.idLibro and ex.disponibilidadEjemplar = 'si') " +
                "and (? is null or l.tituloLibro like concat('%', ?, '%')) " +
                "and (? is null or c.nombreCategoria like concat('%', ?, '%')) " +
                "and (? is null or exists (Select 1 from autor_libro al2 join autor a2 on a2.idAutor = al2.idAutor " +
                "where al2.idLibro = l.idLibro and concat(a2.nombreAutor, ' ', a2.apellidoAutor) like concat('%', ?, '%'))) " +
                "order by l.tituloLibro";
    }

    public String consultarCatalogoPorId() {
        return SELECT_CATALOGO + "where l.idLibro=?";
    }
}