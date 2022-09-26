package com.example.aps_project.contract;

import com.example.aps_project.service.FuzzyQueryResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;


public interface ScheduleTableSearchContract {
    interface IRepository {
        /** 呼叫模糊搜尋 訂單單號API */
        void callQuerySaleOrder(String soId, Callback.fuzzyQuery callback);
        
        /** 呼叫模糊搜尋 客戶名稱API */
        void callQueryCustomerName(String customerName, Callback.fuzzyQuery callback);

        /** 呼叫進度表查詢API */
        void callScheduleTableSearch(String online_date, String sale_order, String customer, Callback.scheduleTable callback);

        /** 讀取進度表查詢結果清單 */
        List<MOResponse> loadScheduleTable();

        /** 取得搜尋結果清單的指定元素值 */
        MOResponse getItemSearchResult(int position);

        class Callback {
            public interface scheduleTable {
                void onScheduleTableResponse(List<MOResponse> searchResultList);
            }

            public interface fuzzyQuery {
                void onFuzzyQueryResponse(List<FuzzyQueryResponse> fuzzyQueryList);
            }
        }
    }

    interface IVew{
        /** 關閉互動視窗 */
        void closeDialog();

        /** 顯示模糊搜尋結果清單 */
        void showQueryResultList(List<FuzzyQueryResponse> resultList, String key);

        /** 跳轉到要顯示查詢結果的Fragment上 */
        void toScheduleTableFragment();

        /** 提示查無結果 */
        void tipNoResult();
    }

    interface IPresenter {
        /** 模糊查詢"訂單單號" */
        void getQuerySaleOrder(String soId);

        /** 模糊查詢"客戶名稱" */
        void getQueryCustomerName(String customerName);

        /** 獲取進度表查詢結果(如果有資料會跳轉到另一個畫面顯示) */
        void getSearcherResult(
            String online_date, //上線日期
            String sale_order,  //訂單單號
            String customer     //客戶名稱
        );
    }
}
