package com.startsoftbr.domestikapro.api;

import android.content.Context;

import com.startsoftbr.domestikapro.session.UserSession;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient(Context ctx) {

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        String token = UserSession.getToken(ctx);

                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .addHeader("Content-Type", "application/json");

                        return chain.proceed(builder.build());
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.14:8082/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
