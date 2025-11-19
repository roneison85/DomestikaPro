package com.startsoftbr.domestikapro.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.R;
import com.startsoftbr.domestikapro.model.FuncionarioResponse;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.VH> {

    public interface OnItemClick {
        void onClick(FuncionarioResponse f);
        void onLongClick(FuncionarioResponse f);
    }

    private List<FuncionarioResponse> lista;
    private final OnItemClick listener;

    public FuncionarioAdapter(List<FuncionarioResponse> lista, OnItemClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void update(List<FuncionarioResponse> novaLista) {
        this.lista = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_funcionario, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        FuncionarioResponse f = lista.get(position);

        holder.txtNome.setText(f.getNome());
        holder.txtFuncao.setText(f.getFuncao());

        holder.itemView.setOnClickListener(v -> listener.onClick(f));
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNome, txtFuncao;
        VH(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtFuncao = itemView.findViewById(R.id.txtFuncao);
        }
    }
}
