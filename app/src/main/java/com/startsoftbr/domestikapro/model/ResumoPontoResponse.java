package com.startsoftbr.domestikapro.model;

import java.util.List;

public class ResumoPontoResponse {
    public Long funcionariaId;
    public String nome;
    public String primeiraEntrada;
    public String ultimaSaida;
    public String totalTrabalhado;
    public String totalPausa;
    public List<RegistroPontoResponse> registros;
}
