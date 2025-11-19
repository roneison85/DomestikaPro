package com.startsoftbr.domestikapro.model;

public class RegistroPontoRequest {
    private Long funcionarioId;
    private String tipo;

    public RegistroPontoRequest(Long funcionarioId, String tipo) {
        this.funcionarioId = funcionarioId;
        this.tipo = tipo;
    }
}
