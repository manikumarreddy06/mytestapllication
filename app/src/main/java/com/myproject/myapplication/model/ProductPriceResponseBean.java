package com.myproject.myapplication.model;

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
}
