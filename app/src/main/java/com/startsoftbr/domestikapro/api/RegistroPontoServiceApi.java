package com.startsoftbr.domestikapro.api;

import com.startsoftbr.domestikapro.model.RegistroPontoRequest;
import com.startsoftbr.domestikapro.model.RegistroPontoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegistroPontoServiceApi {

    @POST("ponto/bater")
    Call<RegistroPontoResponse> bater(@Body RegistroPontoRequest req);

    @GET("ponto/funcionaria/{id}")
    Call<List<RegistroPontoResponse>> listar(@Path("id") Long id);
}
