package com.mv.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "apartamento")
public final class Apartamento {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "apartamento-sequence-generator"
    )
    @SequenceGenerator(
        name = "apartamento-sequence-generator",
        sequenceName = "apartamento_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private int numero;

    @Column(name = "andar")
    private int andar;

    @Column(name = "metragem")
    private double metragem;

    @Column(name = "situacao")
    private String situacao;

    @ManyToOne
    @JoinColumn(name = "edificio_id", nullable = false)
    private Edificio edificio;

    @OneToOne(mappedBy = "apartamento")
    private Morador morador;

    public Apartamento() {
    }

    public Apartamento(int numero, int andar, double metragem, String situacao, Edificio edificio) {
        this.numero = numero;
        this.andar = andar;
        this.metragem = metragem;
        this.situacao = situacao;
        this.edificio = edificio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public double getMetragem() {
        return metragem;
    }

    public void setMetragem(double metragem) {
        this.metragem = metragem;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }
}