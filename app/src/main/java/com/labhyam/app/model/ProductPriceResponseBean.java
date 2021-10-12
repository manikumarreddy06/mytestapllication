package com.labhyam.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductPriceResponseBean implements Serializable {
    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sellingPrice")
    @Expose
    private String sellingPrice;


    @SerializedName("procurementPrice")
    @Expose
    private String procurementPrice;

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProcurementPrice() {
        return procurementPrice;
    }

    public void setProcurementPrice(String procurementPrice) {
        this.procurementPrice = procurementPrice;
    }
}
