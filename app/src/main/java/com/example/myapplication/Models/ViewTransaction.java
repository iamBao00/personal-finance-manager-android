package com.example.myapplication.Models;

import android.os.Build;

import com.example.myapplication.Services.TransactionAPI;

import java.time.LocalDate;
import java.util.Date;

public class ViewTransaction {
    private long amount;
    private String description;
    private String date;
    private String categoryName;
    private String type;

    public ViewTransaction(){}
    public ViewTransaction(long amount, String description, String date, String categoryName, String type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryName = categoryName;
        this.type = type;
    }

    public ViewTransaction(Transaction t, Category c){
        this.amount = t.getAmount();
        this.description = t.getDescription();
        this.date = t.getDate();
        this.categoryName = c.getName();
        this.type = c.getType();
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
