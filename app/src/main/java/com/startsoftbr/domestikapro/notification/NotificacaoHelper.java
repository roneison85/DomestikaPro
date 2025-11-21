package com.startsoftbr.domestikapro.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.startsoftbr.domestikapro.R;

public class NotificacaoHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void mostrar(Context ctx, String titulo, String msg) {
        String canal = "alertas_dp";

        NotificationChannel channel = new NotificationChannel(
                canal,
                "Doméstica Pro - Alertas",
                NotificationManager.IMPORTANCE_HIGH
        );

        NotificationManager nm = ctx.getSystemService(NotificationManager.class);
        nm.createNotificationChannel(channel);

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx, canal)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(titulo)
                .setContentText(msg)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        nm.notify((int) System.currentTimeMillis(), b.build());
    }
}
