package com.example.zidan.learnislam.api;

import com.example.zidan.learnislam.model.Jadwal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("{city}" + ".json")
    Call<Jadwal> getJadwal(@Path("city") String city);
}
