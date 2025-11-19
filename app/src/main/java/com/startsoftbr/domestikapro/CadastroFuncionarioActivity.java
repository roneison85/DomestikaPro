package com.startsoftbr.domestikapro;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.FuncionarioServiceApi;
import com.startsoftbr.domestikapro.model.FuncionarioRequest;
import com.startsoftbr.domestikapro.model.FuncionarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroFuncionarioActivity extends AppCompatActivity {

    private EditText edtNome, edtFuncao;
    private Button btnSalvar;
    private FuncionarioServiceApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);

        edtNome = findViewById(R.id.edtNome);
        edtFuncao = findViewById(R.id.edtFuncao);
        btnSalvar = findViewById(R.id.btnSalvar);

        Retrofit retrofit = ApiClient.getClient(this);
        api = retrofit.create(FuncionarioServiceApi.class);

        btnSalvar.setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String nome = edtNome.getText().toString().trim();
        String funcao = edtFuncao.getText().toString().trim();

        if (nome.isEmpty()) {
            edtNome.setError("Informe o nome");
            return;
        }

        FuncionarioRequest req = new FuncionarioRequest(nome, funcao);

        api.criar(req).enqueue(new Callback<FuncionarioResponse>() {
            @Override
            public void onResponse(Call<FuncionarioResponse> call, Response<FuncionarioResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroFuncionarioActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        Log.e("API_ERROR", "CODE: " + response.code());
                        Log.e("API_ERROR", "BODY: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroFuncionarioActivity.this,
                            "Erro ao salvar (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FuncionarioResponse> call, Throwable t) {
                Toast.makeText(CadastroFuncionarioActivity.this, "Falha de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
