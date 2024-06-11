package com.example.myapplication.Models;

import java.time.LocalDate;
import java.util.Date;

public class ViewTransaction {
    private long amount;
    private String description;
    private LocalDate date;
    private String categoryName;
    private String type;

    public ViewTransaction(){}
    public ViewTransaction(long amount, String description, LocalDate date, String categoryName, String type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryName = categoryName;
        this.type = type;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
