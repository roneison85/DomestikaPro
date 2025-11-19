package com.startsoftbr.domestikapro.api;

import com.startsoftbr.domestikapro.model.FuncionarioRequest;
import com.startsoftbr.domestikapro.model.FuncionarioResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FuncionarioServiceApi {

    @GET("funcionarias")
    Call<List<FuncionarioResponse>> listar();

    @POST("funcionarias")
    Call<FuncionarioResponse> criar(@Body FuncionarioRequest req);

    @PUT("funcionarias/{id}")
    Call<FuncionarioResponse> editar(@Path("id") Long id, @Body FuncionarioRequest req);

    @DELETE("funcionarias/{id}")
    Call<Void> remover(@Path("id") Long id);
}

