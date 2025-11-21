package com.startsoftbr.domestikapro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.startsoftbr.domestikapro.R;
import com.startsoftbr.domestikapro.session.UserSession;
import com.startsoftbr.domestikapro.worker.AlertaWorker;

import java.util.concurrent.TimeUnit;

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

        PeriodicWorkRequest req = new PeriodicWorkRequest.Builder(
                AlertaWorker.class,
                30, TimeUnit.MINUTES
        ).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "alertas_dp",
                ExistingPeriodicWorkPolicy.KEEP,
                req
        );

    }
}
