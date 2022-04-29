package com.example.aps_project.service;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private int status=1;

    @SerializedName("token")
    private String token="token";

    @SerializedName("name") //登入者名稱
    private String userName;

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }
    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}
