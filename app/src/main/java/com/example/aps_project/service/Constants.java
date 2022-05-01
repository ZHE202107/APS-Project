package com.example.aps_project.service;

public final class Constants {
    public static final String BASE_URL = "http://10.0.0.243:8082/api/";
    public static final String LOGIN_URL = "auth/login";      //登入取得Token
    public static final String USER_INFO_URL = "auth/";       //登入人員資訊
    public static final String LOGOUT_ULR = "auth/logout";    //登出
    public static final String FUZZY_QUERY_SALE_ORDER = "app-search-so"; //模糊查詢訂單單號
    public static final String FUZZY_QUERY_CUSTOMER = "app-search-customer";  //模糊查詢客戶名稱

    public static final String GET_MANUFACTURE = "get-manufacture"; //查詢製令單號(MO)
    public static final String GET_CURR_STAGE_MO = "get-current-stage-com";; //查詢本階製令(BOM)
}
