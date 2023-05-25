package com.mv.application.services;

import com.mv.application.model.Morador;
import com.mv.application.repositories.MoradorRepository;
import org.springframework.stereotype.Service;

@Service
public class MoradorService {

    private final MoradorRepository repository;

    public MoradorService(MoradorRepository repository) {
        this.repository = repository;
    }

    public Morador getByApartamentoId(Long id) {
        return this.repository.findByApartamentoId(id);
    }

    public Morador save(Morador morador) {
        return this.repository.save(morador);
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}
