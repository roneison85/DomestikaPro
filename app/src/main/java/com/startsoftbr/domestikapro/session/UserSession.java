package com.startsoftbr.domestikapro.session;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private static final String PREF_NAME = "domestica_pro_prefs";
    private static final String KEY_TOKEN = "jwt_token";

    private static SharedPreferences getPrefs(Context ctx) {
        return ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Salva o token
    public static void saveToken(Context ctx, String token) {
        getPrefs(ctx).edit()
                .putString(KEY_TOKEN, token)
                .apply();
    }

    // Recupera o token
    public static String getToken(Context ctx) {
        return getPrefs(ctx).getString(KEY_TOKEN, "");
    }

    // Remove o token (logout)
    public static void clear(Context ctx) {
        getPrefs(ctx).edit()
                .remove(KEY_TOKEN)
                .apply();
    }
}

