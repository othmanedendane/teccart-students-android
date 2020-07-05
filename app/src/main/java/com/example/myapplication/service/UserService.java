package com.example.myapplication.service;

import com.example.myapplication.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    String API_ROUTE = "users/login.php";
    @Headers({
            "Content-type: application/json"
    })
    @POST(API_ROUTE)
    Call<User> login(@Body User user);

    String CREATE_API_ROUTE = "users/subscribe.php";
    @Headers({
            "Content-type: application/json"
    })
    @POST( CREATE_API_ROUTE)
    Call<User> subscribe(@Body User user);
}