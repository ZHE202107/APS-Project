package com.example.aps_project.service;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public class LoginResponse {
    @SerializedName("status")
    private int status=1;

    @SerializedName("token")
    private String token="token";

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}
