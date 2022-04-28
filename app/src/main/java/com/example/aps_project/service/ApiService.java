package com.example.aps_project.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST(Constants.LOGIN_URL)
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET(Constants.FUZZY_QUERY_SALE_ORDER) //模糊查詢訂單單號
    Call<List<FuzzyQueryResponse>> fuzzyQuerySaleOrder(
        @Query("token") String token,
        @Query("so_id") String so_id
    );

    @GET(Constants.FUZZY_QUERY_CUSTOMER) //模糊查詢客戶名稱
    Call<List<FuzzyQueryResponse>> fuzzyQueryCustomer(
        @Query("token") String token,
        @Query("customer_name") String customer_name
    );
}
