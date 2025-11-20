package com.startsoftbr.domestikapro.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.R;
import com.startsoftbr.domestikapro.model.RegistroPontoResponse;

import java.util.List;

public class RegistrosAdapter extends RecyclerView.Adapter<RegistrosAdapter.VH> {

    private final List<RegistroPontoResponse> lista;

    public RegistrosAdapter(List<RegistroPontoResponse> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_registro_ponto, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        RegistroPontoResponse r = lista.get(pos);

        String hora = r.getDataHora().substring(11, 16);
        h.txt.setText(hora + " - " + r.getTipo());

        switch (r.getTipo()) {
            case "ENTRADA":
                h.txt.setTextColor(Color.parseColor("#2ecc71"));
                break;
            case "PAUSA":
                h.txt.setTextColor(Color.parseColor("#f1c40f"));
                break;
            case "RETORNO":
                h.txt.setTextColor(Color.parseColor("#3498db"));
                break;
            case "SAIDA":
                h.txt.setTextColor(Color.parseColor("#e74c3c"));
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txt;
        VH(View v) {
            super(v);
            txt = v.findViewById(R.id.txtRegistro);
        }
    }
}
