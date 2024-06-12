package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {

    @SerializedName("_id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("category_id")
    private String categoryId;

    @SerializedName("amount")
    private long amount;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    // Constructors, getters and setters
    public Transaction(String id, String userId, String categoryId, long amount, String description, String date) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

