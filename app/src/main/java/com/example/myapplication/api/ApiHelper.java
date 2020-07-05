package com.example.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiHelper {

    private ApiHelper() {
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.12:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
