package com.example.zidan.learnislam.api;

import com.example.zidan.learnislam.model.Jadwal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("bengkulu.json")
    Call<Jadwal> getJadwal();
}
