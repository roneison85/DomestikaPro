package com.startsoftbr.domestikapro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.RegistroPontoServiceApi;
import com.startsoftbr.domestikapro.model.RegistroPontoRequest;
import com.startsoftbr.domestikapro.model.RegistroPontoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FuncionarioDetailActivity extends AppCompatActivity {

    private TextView txtNome, txtFuncao;
    private Button btnEntrada, btnPausa, btnRetorno, btnSaida;
    private ListView listPontos;

    private Long funcionarioId;
    private RegistroPontoServiceApi api;
    private ArrayAdapter<String> adapter;
    private List<String> registrosTexto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_detail);

        txtNome = findViewById(R.id.txtNome);
        txtFuncao = findViewById(R.id.txtFuncao);

        btnEntrada = findViewById(R.id.btnEntrada);
        btnPausa   = findViewById(R.id.btnPausa);
        btnRetorno = findViewById(R.id.btnRetorno);
        btnSaida   = findViewById(R.id.btnSaida);

        listPontos = findViewById(R.id.listPontos);

        // recebe dados da lista
        funcionarioId = getIntent().getLongExtra("id", 0L);
        txtNome.setText(getIntent().getStringExtra("nome"));
        txtFuncao.setText(getIntent().getStringExtra("funcao"));

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(RegistroPontoServiceApi.class);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                registrosTexto);

        listPontos.setAdapter(adapter);

        btnEntrada.setOnClickListener(v -> bater("ENTRADA"));
        btnPausa.setOnClickListener(v -> bater("PAUSA"));
        btnRetorno.setOnClickListener(v -> bater("RETORNO"));
        btnSaida.setOnClickListener(v -> bater("SAIDA"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregar();
    }

    private void bater(String tipo) {
        RegistroPontoRequest req = new RegistroPontoRequest(funcionarioId, tipo);

        api.bater(req).enqueue(new Callback<RegistroPontoResponse>() {
            @Override
            public void onResponse(Call<RegistroPontoResponse> call, Response<RegistroPontoResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FuncionarioDetailActivity.this,
                            tipo + " registrada", Toast.LENGTH_SHORT).show();
                    carregar();
                } else {
                    Toast.makeText(FuncionarioDetailActivity.this,
                            "Erro: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroPontoResponse> call, Throwable t) {
                Toast.makeText(FuncionarioDetailActivity.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregar() {
        api.listar(funcionarioId).enqueue(new Callback<List<RegistroPontoResponse>>() {
            @Override
            public void onResponse(Call<List<RegistroPontoResponse>> call,
                                   Response<List<RegistroPontoResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    registrosTexto.clear();
                    for (RegistroPontoResponse r : response.body()) {
                        registrosTexto.add(r.getDataHora() + " - " + r.getTipo());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<RegistroPontoResponse>> call, Throwable t) {}
        });
    }
}
