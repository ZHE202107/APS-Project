package com.example.aps_project.service;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("account")
    String account;

    @SerializedName("password")
    String password;

    public LoginRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
