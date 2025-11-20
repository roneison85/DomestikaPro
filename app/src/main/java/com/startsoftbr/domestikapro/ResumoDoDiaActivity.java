package com.startsoftbr.domestikapro;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.RegistroPontoServiceApi;
import com.startsoftbr.domestikapro.model.ResumoPontoResponse;
import com.startsoftbr.domestikapro.ui.RegistrosAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResumoDoDiaActivity extends AppCompatActivity {

    private TextView txtFunc, txtEntrada, txtSaida, txtTrabalhado, txtPausa;
    private RecyclerView recycler;
    private RegistrosAdapter adapter;

    private Long funcionarioId;
    private RegistroPontoServiceApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_do_dia);

        txtFunc = findViewById(R.id.txtFunc);
        txtEntrada = findViewById(R.id.txtEntrada);
        txtSaida = findViewById(R.id.txtSaida);
        txtTrabalhado = findViewById(R.id.txtTrabalhado);
        txtPausa = findViewById(R.id.txtPausa);

        recycler = findViewById(R.id.recyclerResumo);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        funcionarioId = getIntent().getLongExtra("id", 0L);

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(RegistroPontoServiceApi.class);

        carregar();
    }

    private void carregar() {
        api.resumo(funcionarioId).enqueue(new Callback<ResumoPontoResponse>() {
            @Override
            public void onResponse(Call<ResumoPontoResponse> call,
                                   Response<ResumoPontoResponse> resp) {
                if (resp.isSuccessful() && resp.body() != null) {

                    ResumoPontoResponse r = resp.body();

                    txtFunc.setText(r.nome);
                    txtEntrada.setText("Entrada: " + r.primeiraEntrada);
                    txtSaida.setText("Saída: " + r.ultimaSaida);
                    txtTrabalhado.setText("Trabalhado: " + r.totalTrabalhado);
                    txtPausa.setText("Pausa: " + r.totalPausa);

                    adapter = new RegistrosAdapter(r.registros);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResumoPontoResponse> call, Throwable t) {}
        });
    }
}
