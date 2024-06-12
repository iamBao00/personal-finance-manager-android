package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class TransactionRequest {
    @SerializedName("category_id")
    private String categoryId;

    @SerializedName("amount")
    private long amount;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    public TransactionRequest(String categoryId, long amount, String description, String date) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
}
