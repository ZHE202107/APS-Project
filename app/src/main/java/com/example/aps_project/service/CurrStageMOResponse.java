package com.example.aps_project.service;

import com.google.gson.annotations.SerializedName;

public class CurrStageMOResponse {
    @SerializedName("unit_id") //單位 ex:PCS
    private String unitId;
    @SerializedName("unit_qty") //單位用量 ex:1.00
    private String unitQty;
    @SerializedName("nuse_qty") //需領用量 ex:3.00
    private String nuseQty;
    @SerializedName("parent")
    private Parent parent;

    //-------o-oo-- Get方法 --oo-o-------
    public String getUnitId() {
        return unitId;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public String getNuseQty() {
        return nuseQty;
    }

    public Parent getParent() {
        return parent;
    }

    //-------x-xx------------xx-x-------

    /**
     * 第二層
     */
    public class Parent {
        @SerializedName("material_id") //材料編號 ex:F2671D-22
        private String materialId;
        @SerializedName("bomkey_name") //品名規格 ex:2671D大抽身(0.8) 新型一?其它不?  75mm
        private String bomkeyName;

        //-------o-oo-- Get方法 --oo-o-------
        public String getMaterialId() {
            return materialId;
        }

        public String getBomkeyName() {
            return bomkeyName;
        }
        //-------x-xx------------xx-x-------
    }
}
