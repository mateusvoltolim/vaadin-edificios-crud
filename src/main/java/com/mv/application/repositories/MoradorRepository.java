package com.mv.application.repositories;

import com.mv.application.model.Morador;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends ListCrudRepository<Morador, Long> {

    Morador findByApartamentoId(Long id);
}
