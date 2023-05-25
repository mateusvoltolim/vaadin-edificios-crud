package com.mv.application.repositories;

import com.mv.application.model.Apartamento;
import com.mv.application.model.Edificio;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartamentoRepository extends ListCrudRepository<Apartamento, Long> {

    List<Apartamento> findApartamentoByEdificioId(Long id);
}
