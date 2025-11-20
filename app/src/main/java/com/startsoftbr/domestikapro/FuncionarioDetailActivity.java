package com.startsoftbr.domestikapro;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.RegistroPontoServiceApi;
import com.startsoftbr.domestikapro.model.RegistroPontoRequest;
import com.startsoftbr.domestikapro.model.RegistroPontoResponse;
import com.startsoftbr.domestikapro.ui.RegistrosAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FuncionarioDetailActivity extends AppCompatActivity {

    private TextView txtNome, txtFuncao;
    private Button btnEntrada, btnPausa, btnRetorno, btnSaida;
    private RecyclerView recycler;
    private RegistrosAdapter adapter;

    private Long funcionarioId;
    private RegistroPontoServiceApi api;

    private List<RegistroPontoResponse> registros = new ArrayList<>();

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

        recycler = findViewById(R.id.recyclerPontos);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegistrosAdapter(registros);
        recycler.setAdapter(adapter);

        funcionarioId = getIntent().getLongExtra("id", 0L);
        txtNome.setText(getIntent().getStringExtra("nome"));
        txtFuncao.setText(getIntent().getStringExtra("funcao"));

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(RegistroPontoServiceApi.class);

        btnEntrada.setOnClickListener(v -> confirmar("ENTRADA"));
        btnPausa.setOnClickListener(v -> confirmar("PAUSA"));
        btnRetorno.setOnClickListener(v -> confirmar("RETORNO"));
        btnSaida.setOnClickListener(v -> confirmar("SAIDA"));

        Button btnResumo = findViewById(R.id.btnResumo);

        btnResumo.setOnClickListener(v -> {
            Intent it = new Intent(this, ResumoDoDiaActivity.class);
            it.putExtra("id", funcionarioId);
            startActivity(it);
        });

        Button btnPdf = findViewById(R.id.btnPdf);
        btnPdf.setOnClickListener(v -> baixarPdf());

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregar();
    }

    private void carregar() {
        api.listar(funcionarioId).enqueue(new Callback<List<RegistroPontoResponse>>() {
            @Override
            public void onResponse(Call<List<RegistroPontoResponse>> call,
                                   Response<List<RegistroPontoResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    registros.clear();
                    registros.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    atualizarBotoes();
                }
            }

            @Override
            public void onFailure(Call<List<RegistroPontoResponse>> call, Throwable t) {}
        });
    }

    private void atualizarBotoes() {
        boolean temEntrada = registros.stream().anyMatch(r -> r.getTipo().equals("ENTRADA"));
        boolean temSaida   = registros.stream().anyMatch(r -> r.getTipo().equals("SAIDA"));

        long pausas   = registros.stream().filter(r -> r.getTipo().equals("PAUSA")).count();
        long retornos = registros.stream().filter(r -> r.getTipo().equals("RETORNO")).count();

        // Lógica inteligente
        if (!temEntrada) {
            // Antes de qualquer ponto
            habilitar(btnEntrada);
            desabilitar(btnPausa, btnRetorno, btnSaida);
            return;
        }

        if (temSaida) {
            // Expediente encerrado
            desabilitar(btnEntrada, btnPausa, btnRetorno, btnSaida);
            return;
        }

        if (pausas > retornos) {
            // Está em pausa
            desabilitar(btnEntrada, btnPausa, btnSaida);
            habilitar(btnRetorno);
            return;
        }

        // Entrada feita, nenhuma pausa aberta
        habilitar(btnPausa, btnSaida);
        desabilitar(btnEntrada, btnRetorno);
    }

    private void habilitar(Button... btns) {
        for (Button b : btns) {
            b.setEnabled(true);
            b.setAlpha(1.0f);
        }
    }

    private void desabilitar(Button... btns) {
        for (Button b : btns) {
            b.setEnabled(false);
            b.setAlpha(0.4f);
        }
    }

    private void confirmar(String tipo) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("Registrar " + tipo + "?")
                .setPositiveButton("Sim", (d, w) -> bater(tipo))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void bater(String tipo) {
        RegistroPontoRequest req = new RegistroPontoRequest(funcionarioId, tipo);

        api.bater(req).enqueue(new Callback<RegistroPontoResponse>() {
            @Override
            public void onResponse(Call<RegistroPontoResponse> call,
                                   Response<RegistroPontoResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(FuncionarioDetailActivity.this,
                            tipo + " registrada!", Toast.LENGTH_SHORT).show();

                    carregar();
                } else {
                    Toast.makeText(FuncionarioDetailActivity.this,
                            "Erro: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroPontoResponse> call, Throwable t) {
                Toast.makeText(FuncionarioDetailActivity.this,
                        "Falha de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void baixarPdf() {
        api.baixarPdf(funcionarioId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    salvarEabrirPdf(response.body());
                } else {
                    Toast.makeText(FuncionarioDetailActivity.this,
                            "Erro ao baixar PDF", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FuncionarioDetailActivity.this,
                        "Falha de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarEabrirPdf(ResponseBody body) {
        try {
            File file = new File(getExternalCacheDir(), "relatorio.pdf");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(body.bytes());
            fos.close();

            Uri uri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    file
            );

            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setDataAndType(uri, "application/pdf");
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(it);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao abrir PDF", Toast.LENGTH_SHORT).show();
        }
    }


}
