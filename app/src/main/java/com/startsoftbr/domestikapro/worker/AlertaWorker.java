package com.startsoftbr.domestikapro.worker;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.startsoftbr.domestikapro.api.ApiClient;
import com.startsoftbr.domestikapro.api.RegistroPontoServiceApi;
import com.startsoftbr.domestikapro.model.DashboardResponse;
import com.startsoftbr.domestikapro.notification.NotificacaoHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlertaWorker extends Worker {

    public AlertaWorker(@NonNull Context ctx, @NonNull WorkerParameters params) {
        super(ctx, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        Retrofit retrofit = ApiClient.getClient(getApplicationContext());
        RegistroPontoServiceApi api = retrofit.create(RegistroPontoServiceApi.class);

        api.dashboard().enqueue(new Callback<DashboardResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> r) {

                if (!r.isSuccessful() || r.body() == null) return;

                for (DashboardResponse.AlertaPonto a : r.body().alertas) {
                    NotificacaoHelper.mostrar(
                            getApplicationContext(),
                            a.tipo,
                            a.mensagem
                    );
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {}
        });

        return Result.success();
    }
}
