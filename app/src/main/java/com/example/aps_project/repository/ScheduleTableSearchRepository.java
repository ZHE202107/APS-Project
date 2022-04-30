package com.example.aps_project.repository;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.aps_project.ApiClient;
import com.example.aps_project.SessionManager;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.FuzzyQueryResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ScheduleTableSearchRepository implements ScheduleTableSearchContract.IRepository {
    private static ApiService apiService;
    private SessionManager sessionManager;
    private List<FuzzyQueryResponse> fuzzyQueryList;    //儲存模糊搜尋結果
    private static List<MOResponse> searchResultList;    //Search Result List (進度表搜尋結果清單)
    private static String token;

    public ScheduleTableSearchRepository(Fragment view) {
        apiService = new ApiClient().getApiService();
        sessionManager = new SessionManager(view.getContext());
        token = sessionManager.fetchAuthToken(); //獲取token
    }

    @Override
    public void callQuerySaleOrder(String soId, Callback.fuzzyQuery callback) {
        apiService.fuzzyQuerySaleOrder(token, soId)
            .enqueue(new retrofit2.Callback<List<FuzzyQueryResponse>>() {
                @Override
                public void onResponse(Call<List<FuzzyQueryResponse>> call, Response<List<FuzzyQueryResponse>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        fuzzyQueryList = response.body(); //儲存結果
                        callback.onFuzzyQueryResponse(fuzzyQueryList);
                    }else{
                        Log.e("www", "顯示模糊查詢訂單單號結果 獲取失敗!!!!");
                    }
                }

                @Override
                public void onFailure(Call<List<FuzzyQueryResponse>> call, Throwable t) {
                    Log.e("www", "模糊查詢訂單_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public void callQueryCustomerName(String customerName, Callback.fuzzyQuery callback) {
        apiService.fuzzyQueryCustomer(token, customerName)
            .enqueue(new retrofit2.Callback<List<FuzzyQueryResponse>>() {
                @Override
                public void onResponse(Call<List<FuzzyQueryResponse>> call, Response<List<FuzzyQueryResponse>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        fuzzyQueryList = response.body();   //儲存結果
                        callback.onFuzzyQueryResponse(fuzzyQueryList);
                    }else{
                        Log.e("www", "顯示模糊查詢客戶名稱 獲取失敗!!!!");
                    }
                }

                @Override
                public void onFailure(Call<List<FuzzyQueryResponse>> call, Throwable t) {
                    Log.e("www", "模糊查詢客戶名稱_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public void callScheduleTableSearch(String online_date, String sale_order, String customer, Callback.scheduleTable callback) {
        //呼叫API搜尋
        apiService.getManufactureOrder(sessionManager.fetchAuthToken(), online_date, sale_order, customer)
            .enqueue(new retrofit2.Callback<List<MOResponse>>() {
                @Override
                public void onResponse(Call<List<MOResponse>> call, Response<List<MOResponse>> response) {
                    if(response.code() == 200 && response.body() != null) {
                        searchResultList = response.body();
                        callback.onScheduleTableResponse(searchResultList);
                    } else {
                        Log.e("www", "進度表查詢結果_抓取失敗!!!!!\n" + response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<MOResponse>> call, Throwable t) {
                    Log.e("www", "進度表查詢結果_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public List<MOResponse> loadScheduleTable() {
        Log.e("www", "public List<MOResponse> loadScheduleTable() {" + searchResultList.size());
        return searchResultList;
    }

    @Override
    public MOResponse getItemSearchResult(int position) {
        Log.e("www", "MOResponse Repository：" + searchResultList.size());
        return searchResultList.get(position);
    }
}
