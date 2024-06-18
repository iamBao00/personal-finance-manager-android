package com.example.myapplication.Services;

import com.example.myapplication.Models.Transaction;
import com.example.myapplication.Models.TransactionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransactionAPI {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    TransactionAPI transactionAPI = new Retrofit.Builder()
            .baseUrl("http://172.20.10.2:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(TransactionAPI.class);

    @GET("/api/transaction/list")
    Call<ArrayList<Transaction>> getTransactions(@Header("Authorization") String token);

    @GET("/api/transaction/date")
    Call<ArrayList<Transaction>> getTransactionsWithFilterDate(
            @Header("Authorization") String token,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @POST("/api/transaction/add")
    Call<TransactionRequest> addTransaction(
            @Header("Authorization") String token,
            @Body TransactionRequest transactionRequest
    );
}
