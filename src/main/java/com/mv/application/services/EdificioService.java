package com.mv.application.services;

import com.mv.application.model.Edificio;
import com.mv.application.repositories.EdificioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EdificioService {

    private final EdificioRepository repository;

    public EdificioService(EdificioRepository repository) {
        this.repository = repository;
    }

    public List<Edificio> all() {
        return this.repository.findAll();
    }

    public void save(Edificio e) {
        this.repository.save(e);
    }

}
