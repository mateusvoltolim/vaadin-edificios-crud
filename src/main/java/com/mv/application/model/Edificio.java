package com.mv.application.model;

import java.util.List;
import java.util.Objects;

public record Edificio(int id, String nome, String endereco, List<Apartamento> apartamentos) {

    public void adicionarApartamento(Apartamento apartamento) {
        this.apartamentos.add(apartamento);
    }

    public String mostrarApartamentos() {
        return this.apartamentos.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edificio edificio = (Edificio) o;
        return id == edificio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
