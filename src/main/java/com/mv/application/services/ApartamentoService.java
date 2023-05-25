package com.mv.application.services;

import com.mv.application.model.Apartamento;
import com.mv.application.repositories.ApartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartamentoService {

    private final ApartamentoRepository repository;

    public ApartamentoService(ApartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Apartamento> getByEdificioId(Long id) {
        return this.repository.findApartamentoByEdificioId(id);
    }

    public void save(Apartamento apartamento) {
        this.repository.save(apartamento);
    }
}
