package com.startsoftbr.domestikapro;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.RegistroPontoServiceApi;
import com.startsoftbr.domestikapro.model.DashboardResponse;
import com.startsoftbr.domestikapro.ui.RankingAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends AppCompatActivity {

    private TextView txtData, txtFuncionarias, txtEntradas, txtSaidas, txtPausas, txtRetornos, txtTrab, txtPausa;
    private RecyclerView recyclerRanking;
    private RankingAdapter adapter;
    private RegistroPontoServiceApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtData = findViewById(R.id.txtData);
        txtFuncionarias = findViewById(R.id.txtFuncionarias);
        txtEntradas = findViewById(R.id.txtEntradas);
        txtSaidas = findViewById(R.id.txtSaidas);
        txtPausas = findViewById(R.id.txtPausas);
        txtRetornos = findViewById(R.id.txtRetornos);
        txtTrab = findViewById(R.id.txtTrab);
        txtPausa = findViewById(R.id.txtPausa);

        recyclerRanking = findViewById(R.id.recyclerRanking);
        recyclerRanking.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(RegistroPontoServiceApi.class);

        carregar();
    }

    private void carregar() {
        api.dashboard().enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> r) {
                if (r.isSuccessful() && r.body() != null) {
                    DashboardResponse d = r.body();

                    txtData.setText("Data: " + d.data);
                    txtFuncionarias.setText("Total de Funcionárias: " + d.totalFuncionarias);
                    txtEntradas.setText("Entradas: " + d.totalEntradas);
                    txtSaidas.setText("Saídas: " + d.totalSaidas);
                    txtPausas.setText("Pausas: " + d.totalPausas);
                    txtRetornos.setText("Retornos: " + d.totalRetornos);
                    txtTrab.setText("Total Trabalhado: " + d.totalTrabalhado);
                    txtPausa.setText("Total Pausa: " + d.totalPausa);

                    adapter = new RankingAdapter(d.ranking);
                    recyclerRanking.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {}
        });
    }
}
