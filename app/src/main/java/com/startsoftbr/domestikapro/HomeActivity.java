package com.startsoftbr.domestikapro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.FuncionarioServiceApi;
import com.startsoftbr.domestikapro.model.FuncionarioResponse;
import com.startsoftbr.domestikapro.ui.FuncionarioAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private FloatingActionButton fabAdd;
    private FuncionarioAdapter adapter;
    private FuncionarioServiceApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = findViewById(R.id.recyclerFuncionarios);
        fabAdd = findViewById(R.id.fabAdd);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(FuncionarioServiceApi.class);

        adapter = new FuncionarioAdapter(new ArrayList<>(), new FuncionarioAdapter.OnItemClick() {
            @Override
            public void onClick(FuncionarioResponse f) {
                Intent it = new Intent(HomeActivity.this, FuncionarioDetailActivity.class);
                it.putExtra("id", f.getId());
                it.putExtra("nome", f.getNome());
                it.putExtra("funcao", f.getFuncao());
                startActivity(it);
            }

            @Override
            public void onLongClick(FuncionarioResponse f) {
                // futuro: excluir
            }
        });

        recycler.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent it = new Intent(HomeActivity.this, CadastroFuncionarioActivity.class);
            startActivity(it);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFuncionarias();
    }

    private void carregarFuncionarias() {
        api.listar().enqueue(new Callback<List<FuncionarioResponse>>() {
            @Override
            public void onResponse(Call<List<FuncionarioResponse>> call, Response<List<FuncionarioResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.update(response.body());
                } else {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar funcionárias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FuncionarioResponse>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Falha de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
