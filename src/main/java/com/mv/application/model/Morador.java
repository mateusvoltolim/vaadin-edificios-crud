package com.mv.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "morador")
public final class Morador {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "morador-sequence-generator"
    )
    @SequenceGenerator(
        name = "morador-sequence-generator",
        sequenceName = "morador_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "apartamento_id", nullable = false)
    private Apartamento apartamento;

    public Morador() {
    }

    public Morador(String nome, String cpf, String telefone, Apartamento apartamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.apartamento = apartamento;
    }

    public Morador(Long id, String nome, String cpf, String telefone, Apartamento apartamento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.apartamento = apartamento;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

}
