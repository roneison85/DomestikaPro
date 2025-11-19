package com.startsoftbr.domestikapro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.AuthService;
import com.startsoftbr.domestikapro.api.TokenManager;
import com.startsoftbr.domestikapro.model.AuthRequest;
import com.startsoftbr.domestikapro.model.AuthResponse;
import com.startsoftbr.domestikapro.session.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnEntrar;
    ProgressBar progressBar;

    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        progressBar = findViewById(R.id.progressBar); // adiciona no layout

        Retrofit retrofit = ApiClient.getClient(this);
        authService = retrofit.create(AuthService.class);

        btnEntrar.setOnClickListener(v -> fazerLogin());
    }

    private void fazerLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnEntrar.setEnabled(false);

        AuthRequest req = new AuthRequest(email, senha);
        authService.login(req).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnEntrar.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse body = response.body();
                    TokenManager.saveToken(LoginActivity.this, body.getToken());

                    UserSession.saveToken(LoginActivity.this, body.getToken());

                    Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                    it.putExtra("nome_usuario", body.getNome());
                    startActivity(it);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login inválido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnEntrar.setEnabled(true);
                System.out.println(":-:-: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
