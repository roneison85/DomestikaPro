package com.startsoftbr.domestikapro.api;

import android.content.Context;

public class TokenManager {

    private static final String PREF = "DOMESTICA_PRO_PREF";
    private static final String KEY = "TOKEN";

    public static void saveToken(Context ctx, String token) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(KEY, token).apply();
    }

    public static String getToken(Context ctx) {
        return ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .getString(KEY, null);
    }

    public static void clear(Context ctx) {
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }
}

