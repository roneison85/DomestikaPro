package com.startsoftbr.domestikapro.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.model.DashboardResponse;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.VH> {

    private List<DashboardResponse.RankingItem> lista;

    public RankingAdapter(List<DashboardResponse.RankingItem> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        DashboardResponse.RankingItem r = lista.get(pos);
        h.txt1.setText((pos + 1) + "º - " + r.funcionaria);
        h.txt2.setText("Trabalhado: " + r.totalTrabalhado);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        VH(View v) {
            super(v);
            txt1 = v.findViewById(android.R.id.text1);
            txt2 = v.findViewById(android.R.id.text2);
        }
    }
}
