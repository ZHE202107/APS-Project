package com.example.aps_project.service;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class FuzzyQueryResponse {
    public static final String SO_ID_KEY = "so_id";
    public static final String CUSTOMER_NAME_KEY = "customer_name";

    @SerializedName(SO_ID_KEY)
    private String so_id = "";

    @SerializedName(CUSTOMER_NAME_KEY)
    private String customer_name = "";

    public String getCustomer_name() {
        return customer_name;
    }
    public String getSo_id() {
        return so_id;
    }

    public String getItemValue(@NonNull String key) {
        switch (key) {
            case SO_ID_KEY:
                return so_id;
            case CUSTOMER_NAME_KEY:
                return customer_name;
            default: return "";
        }
    }
}
