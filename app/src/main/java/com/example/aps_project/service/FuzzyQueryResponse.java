package com.example.aps_project.service;

import com.google.gson.annotations.SerializedName;

public class FuzzyQueryResponse {
    @SerializedName("so_id")
    private String so_id = "";

    @SerializedName("customer_name")
    private String customer_name = "";

    public String getCustomer_name() {
        return customer_name;
    }
    public String getSo_id() {
        return so_id;
    }

    public String getItemValue(String key) {
        switch (key) {
            case "so_id":
                return so_id;
            case "customer_name":
                return customer_name;
            default: return "";
        }
    }
}
