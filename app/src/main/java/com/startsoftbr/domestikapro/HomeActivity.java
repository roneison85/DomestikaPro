package com.startsoftbr.domestikapro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.startsoftbr.domestikapro.session.UserSession;

public class HomeActivity extends AppCompatActivity {

    Button btnFuncionarias, btnDashboard, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFuncionarias = findViewById(R.id.btnFuncionarias);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnLogout = findViewById(R.id.btnLogout);

        btnFuncionarias.setOnClickListener(v -> {
            startActivity(new Intent(this, FuncionariosActivity.class));
        });

        btnDashboard.setOnClickListener(v -> {
            startActivity(new Intent(this, DashboardActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            UserSession.clear(this);
            Intent it = new Intent(this, LoginActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
        });
    }
}
