package com.example.aps_project;

import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiService apiService;

    public ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        apiService = retrofit.create(ApiService.class);
        return  apiService;
    }
}
