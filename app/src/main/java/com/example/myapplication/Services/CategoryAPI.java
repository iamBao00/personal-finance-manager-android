package com.example.myapplication.Services;

import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface CategoryAPI {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    CategoryAPI categoryAPI = new Retrofit.Builder()
            .baseUrl("http://169.254.76.104:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CategoryAPI.class);
    @GET("/api/category/")
    Call<ArrayList<Category>> getCategories(@Header("Authorization") String token);
    @GET("/api/category/{id}")
    Call<Category> getCategoryById(@Header("Authorization") String token, @Path("id") String id);
}
