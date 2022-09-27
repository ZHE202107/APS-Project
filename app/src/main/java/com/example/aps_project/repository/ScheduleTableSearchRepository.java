package com.example.aps_project.repository;

import android.content.Context;
import android.util.Log;

import com.example.aps_project.SessionManager;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.FuzzyQueryResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class ScheduleTableSearchRepository implements ScheduleTableSearchContract.IRepository {
    @Inject
    public ApiService apiService;
    public SessionManager sessionManager;
    private List<FuzzyQueryResponse> fuzzyQueryList;    //儲存模糊搜尋結果
    private static List<MOResponse> searchResultList;    //Search Result List (進度表搜尋結果清單)
    private static String token;

    @Inject
    public ScheduleTableSearchRepository(@ApplicationContext Context context) {
        sessionManager = new SessionManager(context);
        token = sessionManager.fetchAuthToken(); //獲取token
    }

    @Override
    public void callQuerySaleOrder(String soId, Callback.fuzzyQuery callback) {
        apiService.fuzzyQuerySaleOrder(token, soId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSingleObserver<Response<List<FuzzyQueryResponse>>>() {
                @Override
                public void onSuccess(@NonNull Response<List<FuzzyQueryResponse>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        fuzzyQueryList = response.body(); //儲存結果
                        callback.onFuzzyQueryResponse(fuzzyQueryList);
                    }else{
                        Log.e("www", "顯示模糊查詢訂單單號結果 獲取失敗!!!!");
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e("www", "模糊查詢訂單_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public void callQueryCustomerName(String customerName, Callback.fuzzyQuery callback) {
        apiService.fuzzyQueryCustomer(token, customerName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSingleObserver<Response<List<FuzzyQueryResponse>>>() {
                @Override
                public void onSuccess(@NonNull Response<List<FuzzyQueryResponse>> response) {
                    if (response.code() == 200 && response.body() != null) {
                        fuzzyQueryList = response.body();   //儲存結果
                        callback.onFuzzyQueryResponse(fuzzyQueryList);
                    }else{
                        Log.e("www", "顯示模糊查詢客戶名稱 獲取失敗!!!!");
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e("www", "模糊查詢客戶名稱_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public void callScheduleTableSearch(String online_date, String sale_order, String customer, Callback.scheduleTable callback) {
        //呼叫進度表查詢API去搜尋
        apiService.getManufactureOrder(sessionManager.fetchAuthToken(), online_date, sale_order, customer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSingleObserver<Response<List<MOResponse>>>() {
                @Override
                public void onSuccess(@NonNull Response<List<MOResponse>> response) {
                    if(response.code() == 200 && response.body() != null) {
                        searchResultList = response.body();
                        callback.onScheduleTableResponse(searchResultList);
                    } else {
                        Log.e("www", "進度表查詢結果_抓取失敗!!!!!\n" + response.body().toString());
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e("www", "進度表查詢結果_抓取失敗!!!!!\n" + t);
                }
            });
    }

    @Override
    public List<MOResponse> loadScheduleTable() {
        return searchResultList;
    }

    @Override
    public MOResponse getItemSearchResult(int position) {
        if(position == -1) return null;
        return searchResultList.get(position);
    }
}
