package com.mv.application.model;

public enum Situacao {
    ALUGADO("alugado"),
    FINANCIADO("financiado"),
    QUITADO("quitado");

    private final String descricao;

    Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
