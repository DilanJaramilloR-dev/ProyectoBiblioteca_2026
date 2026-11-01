package edu.itm.proyecto2026.jpaRepositories;

import edu.itm.proyecto2026.jpaEntities.Editorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorialRepository extends CrudRepository<Editorial, Integer> {
    List<Editorial> findAll();
}
