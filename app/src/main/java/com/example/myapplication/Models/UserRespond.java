package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

public class UserRespond {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserDetail userDetail;

    // Inner class representing user details
    public static class UserDetail {
        @SerializedName("_id")
        private String id;

        @SerializedName("email")
        private String email;

        @SerializedName("username")
        private String username;

        @SerializedName("balance")
        private long Balance;

        // Constructors, getters, setters


        public long getBalance() {
            return Balance;
        }

        public void setBalance(long balance) {
            Balance = balance;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public UserRespond(String token, UserDetail userDetail) {
        this.token = token;
        this.userDetail = userDetail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
// Constructors, getters, setters
}
