package com.startsoftbr.domestikapro.model;

public class RegistroPontoResponse {
    private Long id;
    private Long funcionarioId;
    private String tipo;
    private String dataHora;

    public Long getId() { return id; }
    public Long getFuncionarioId() { return funcionarioId; }
    public String getTipo() { return tipo; }
    public String getDataHora() { return dataHora; }
}

