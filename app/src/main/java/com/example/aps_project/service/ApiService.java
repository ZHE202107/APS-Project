package com.example.aps_project.service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //登入取得Token
    @POST(Constants.LOGIN_URL)
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

    //獲取登入人員資訊
    @GET(Constants.USER_INFO_URL)
    Single<Response<LoginResponse>> fetchUserInfo(@Query("token") String token);

    //模糊查詢訂單單號
    @GET(Constants.FUZZY_QUERY_SALE_ORDER)
    Single<Response<List<FuzzyQueryResponse>>> fuzzyQuerySaleOrder(
        @Query("token") String token,
        @Query("so_id") String so_id
    );

    //模糊查詢客戶名稱
    @GET(Constants.FUZZY_QUERY_CUSTOMER)
    Single<Response<List<FuzzyQueryResponse>>> fuzzyQueryCustomer(
        @Query("token") String token,
        @Query("customer_name") String customer_name
    );

    //查詢製令單號(MO)
    @GET(Constants.GET_MANUFACTURE)
    Single<Response<List<MOResponse>>> getManufactureOrder(
        @Query("token") String token,
        @Query("online_date") String online_date,
        @Query("sale_order") String sale_order,
        @Query("customer") String customer
    );

    //查詢本階製令(BOM)
    @GET(Constants.GET_CURR_STAGE_MO)
    Single<Response<List<CurrStageMOResponse>>> getCurrStageMO(
        @Query("token") String token,
        @Query("item_id") String itemId
    );
}
