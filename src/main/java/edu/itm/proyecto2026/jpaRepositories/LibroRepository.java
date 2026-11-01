package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Libro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends CrudRepository<Libro, Integer> {
    List<Libro> findAll();
}
