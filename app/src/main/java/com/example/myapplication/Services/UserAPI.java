package com.example.myapplication.Services;

import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.UserRespond;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPI {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    UserAPI userAPI = new Retrofit.Builder()
                    .baseUrl("http://172.20.10.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(UserAPI.class);

//    @FormUrlEncoded
//    @POST("/api/users/login")
//    Call<User> login(@Field("username") String username, @Field("password") String password);
    @POST("/api/users/login")
    Call<UserRespond> login(@Body LoginRequest loginRequest);


    @FormUrlEncoded
    @POST("/api/users/register")
    Call<UserRespond> signUp(@Field("username") String username, @Field("email") String email, @Field("password")String password);
}

