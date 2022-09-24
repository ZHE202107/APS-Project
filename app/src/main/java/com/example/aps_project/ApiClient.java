package com.example.aps_project;

import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiService apiService;

    public ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
        apiService = retrofit.create(ApiService.class);
        return  apiService;
    }
}
