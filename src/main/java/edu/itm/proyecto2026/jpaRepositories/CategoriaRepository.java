package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
    List<Categoria> findAll();
}