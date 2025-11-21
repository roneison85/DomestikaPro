package com.startsoftbr.domestikapro.model;

import java.util.List;

public class DashboardResponse {

    public String data;
    public int totalFuncionarias;

    public long totalEntradas;
    public long totalSaidas;
    public long totalPausas;
    public long totalRetornos;

    public String totalTrabalhado;
    public String totalPausa;

    public List<RankingItem> ranking;
    public List<AlertaPonto> alertas;

    public static class RankingItem {
        public String funcionaria;
        public String totalTrabalhado;
    }

    public static class AlertaPonto {
        public String tipo;
        public String mensagem;
        public Long funcionarioId;
        public String funcionarioNome;
    }
}
