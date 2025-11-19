package com.startsoftbr.domestikapro.api;

import com.startsoftbr.domestikapro.model.AuthRequest;
import com.startsoftbr.domestikapro.model.AuthResponse;
import com.startsoftbr.domestikapro.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST("auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);
}
