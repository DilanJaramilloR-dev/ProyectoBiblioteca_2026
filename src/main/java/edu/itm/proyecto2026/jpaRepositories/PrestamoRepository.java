package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Prestamo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends CrudRepository<Prestamo, Integer> {
    List<Prestamo> findAll();

    @Query("select p from Prestamo p "
            + "where p.fechaDevolucionReal is null and p.estadoPrestamo = 'activo' "
            + "and p.fechaDevolucionPrevista >= :hoy "
            + "order by p.fechaDevolucionPrevista")
    List<Prestamo> buscarActivos(@Param("hoy") LocalDate hoy);

    @Query("select p from Prestamo p "
            + "where p.fechaDevolucionReal is null "
            + "and (p.estadoPrestamo = 'vencido' or p.fechaDevolucionPrevista < :hoy) "
            + "order by p.fechaDevolucionPrevista")
    List<Prestamo> buscarVencidos(@Param("hoy") LocalDate hoy);
}