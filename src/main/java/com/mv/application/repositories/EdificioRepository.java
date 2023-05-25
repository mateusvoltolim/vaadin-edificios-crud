package com.mv.application.repositories;

import com.mv.application.model.Edificio;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdificioRepository extends ListCrudRepository<Edificio, Long> {
}
