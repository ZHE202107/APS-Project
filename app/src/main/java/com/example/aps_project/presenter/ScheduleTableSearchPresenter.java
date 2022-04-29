package com.example.aps_project.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aps_project.ApiClient;
import com.example.aps_project.SessionManager;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.model.ScheduleTableSearchRepository;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.FuzzyQueryResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleTableSearchPresenter implements ScheduleTableSearchContract.IPresenter {
    private ScheduleTableSearchContract.IVew view;
    private ScheduleTableSearchContract.IRepository repository;

    public ScheduleTableSearchPresenter(@NonNull Fragment view) {
        this.view = (ScheduleTableSearchContract.IVew)view;
        repository = new ScheduleTableSearchRepository(view);
    }

    //取得模糊搜尋 訂單單號
    @Override
    public void getQuerySaleOrder(String soId) {
        //呼叫API
        repository.callQuerySaleOrder(soId, fuzzyQueryList -> {
            //傳入訂單單號結果清單，和指定Response欄位
            view.showQueryResultList(fuzzyQueryList, FuzzyQueryResponse.SO_ID_KEY);
        });
    }

    //取得模糊搜尋 客戶名單
    @Override
    public void getQueryCustomerName(String customerName) {
        //呼叫API
        repository.callQueryCustomerName(customerName, fuzzyQueryList -> {
            //傳入客戶名稱結果清單，和指定Response欄位
            view.showQueryResultList(fuzzyQueryList, FuzzyQueryResponse.CUSTOMER_NAME_KEY);
        });
    }

    //獲取進度表查詢結果
    @Override
    public void getSearcherResult(
            String online_date, //上線日期
            String sale_order,  //訂單單號
            String customer     //客戶名稱
    ) {
        //如果都沒有輸入 則直接提示查無結果(避免過度呼叫API)
        if(online_date.equals("") && sale_order.equals("") && customer.equals("")) {
            view.tipNoResult();
            return;
        }
        //呼叫API搜尋
        repository.callScheduleTableSearch(
            online_date,
            sale_order,
            customer,
            searchResultList -> {
                if (searchResultList.size() == 0) {
                    view.tipNoResult(); //查無結果
                } else {
                    //顯示搜尋到的結果(跳轉到顯示結果的Fragment上)
                    view.toScheduleTableFragment();
                }
            }
        );

    }
}
