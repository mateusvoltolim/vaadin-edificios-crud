package com.mv.application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "edificio")
public final class Edificio {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "edificio-sequence-generator"
    )
    @SequenceGenerator(
        name = "edificio-sequence-generator",
        sequenceName = "edificio_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "edificio")
    private List<Apartamento> apartamentos;

    public Edificio() {
    }

    public Edificio(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }

    public void setApartamentos(List<Apartamento> apartamentos) {
        this.apartamentos = apartamentos;
    }
}
