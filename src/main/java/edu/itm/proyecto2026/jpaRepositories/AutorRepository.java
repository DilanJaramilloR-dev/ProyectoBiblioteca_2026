package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Autor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends CrudRepository<Autor, Integer> {
    List<Autor> findAll();
}