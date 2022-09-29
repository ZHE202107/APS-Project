package com.example.aps_project.service;

import com.google.gson.annotations.SerializedName;

/**
 * Manufacture Order Response (本階製令)
 * */
public class MOResponse {
    @SerializedName("mo_id")    //製令單號(MO)
    private String moId;
    @SerializedName("so_id")    //訂單單號(SO)
    private String soId;
    @SerializedName("item_id")  //母件代碼
    private String itemId;
    @SerializedName("customer") //客戶名稱
    private String customer;
    @SerializedName("qty")  //數量
    private String quantity;
    @SerializedName("demand_complete_date") //結關日期
    private String deadline;
    @SerializedName("online_date") //上線日期
    private String onlineDate;
    @SerializedName("related_tech_route") //相關工藝路線
    private RelatedTechRoute relatedTechRoute;

    @SerializedName("item_name")    //母件名稱
    private String itemName;

    /**
      * 以下都是屬性的get方法
      */
    public String getMoId() {
        return moId;
    }

    public String getSoId() {
        return soId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public String getItemName() {
        return itemName;
    }

    public RelatedTechRoute getRelatedTechRoute() {
        return relatedTechRoute;
    }

    //相關工藝路線
    public class RelatedTechRoute {
        @SerializedName("tech_routing_name") //工藝路線名稱
        private String techRouteName;

        public String getTechRouteName() {
            return techRouteName;
        }
    }
}
