package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class ReporteDAOHelper {

    private static final String SELECT_PRESTAMOS =
            "Select p.idPrestamo, ej.codigoInventario, l.tituloLibro, " +
                    "concat(pu.nombrePersona, ' ', pu.apellidoPersona) as nombreUsuario, " +
                    "concat(pb.nombrePersona, ' ', pb.apellidoPersona) as nombreBibliotecario, " +
                    "p.estadoPrestamo, p.fechaPrestamo, p.fechaDevolucionPrevista, " +
                    "greatest(datediff(curdate(), p.fechaDevolucionPrevista), 0) as diasRetraso " +
                    "from prestamo p " +
                    "join ejemplar ej on ej.idEjemplar = p.idEjemplar " +
                    "join libro l on l.idLibro = ej.idLibro " +
                    "join usuario u on u.idUsuario = p.idUsuario " +
                    "join persona pu on pu.idPersona = u.idPersona " +
                    "join bibliotecario b on b.idBibliotecario = p.idBibliotecario " +
                    "join persona pb on pb.idPersona = b.idPersona ";

    public String listarPrestamosActivos() {
        return SELECT_PRESTAMOS +
                "where p.fechaDevolucionReal is null and p.estadoPrestamo = 'activo' " +
                "and p.fechaDevolucionPrevista >= curdate() " +
                "order by p.fechaDevolucionPrevista";
    }

    public String listarPrestamosVencidos() {
        return SELECT_PRESTAMOS +
                "where p.fechaDevolucionReal is null " +
                "and (p.estadoPrestamo = 'vencido' or p.fechaDevolucionPrevista < curdate()) " +
                "order by p.fechaDevolucionPrevista";
    }

    public String listarInventario() {
        return "Select l.idLibro, l.tituloLibro, l.isbnLibro, c.nombreCategoria, " +
                "(Select count(*) from ejemplar ej where ej.idLibro = l.idLibro) as totalEjemplares, " +
                "(Select count(*) from ejemplar ed where ed.idLibro = l.idLibro and ed.disponibilidadEjemplar = 'si') as ejemplaresDisponibles " +
                "from libro l join categoria c on c.idCategoria = l.idCategoria " +
                "order by l.tituloLibro";
    }
}