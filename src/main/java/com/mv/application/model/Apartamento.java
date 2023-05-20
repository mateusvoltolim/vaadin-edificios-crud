package com.mv.application.model;

public record Apartamento(int numero, int andar, double metragem, String situacao, Morador morador) {
}