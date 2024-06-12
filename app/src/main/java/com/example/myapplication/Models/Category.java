package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    private String name;
    private String type;

    // Getters and setters
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
