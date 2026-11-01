package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Libro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends CrudRepository<Libro, Integer> {
    List<Libro> findAll();

    List<Libro> findAllByOrderByTituloLibroAsc();

    @Query("select l from Libro l "
            + "where exists (select e.idEjemplar from Ejemplar e "
            + "where e.libro = l and e.disponibilidadEjemplar = 'si') "
            + "and l.tituloLibro like concat('%', :titulo, '%') "
            + "and l.categoria.nombreCategoria like concat('%', :categoria, '%') "
            + "and (:autor = '' or exists (select a.idAutor from l.autores a "
            + "where concat(a.nombreAutor, ' ', a.apellidoAutor) like concat('%', :autor, '%'))) "
            + "order by l.tituloLibro")
    List<Libro> buscarCatalogo(@Param("titulo") String titulo,
                               @Param("autor") String autor,
                               @Param("categoria") String categoria);
}
