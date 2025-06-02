package ru.mirea.tenyutin.mireaproject.ui.network;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CatApi {
    @GET("/fact")
    Call<CatFact> getFact();
}