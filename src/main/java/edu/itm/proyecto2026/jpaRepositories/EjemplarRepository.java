package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Ejemplar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjemplarRepository extends CrudRepository<Ejemplar, Integer> {
    List<Ejemplar> findAll();
}