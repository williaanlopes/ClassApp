package com.gurpster.facapemobile.model;

/**
 * Created by Williaan Lopes (d3x773r) on 17/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public enum Status {

    Matriculado(1), Aprovado(2), Reprovado(3);

    private int valor;

    Status(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
