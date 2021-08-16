package com.myproject.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GettingvariantResponsebean implements Serializable {
    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sellingPrice")
    @Expose
    private String sellingPrice;
    private final static long serialVersionUID = 5919985941319392461L;

    public boolean isIsvalid() { return isvalid; }

    public void setIsvalid(boolean isvalid) { this.isvalid = isvalid; }

    public GettingvariantResponsebean withIsvalid(boolean isvalid) { this.isvalid = isvalid;return this; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
    public  GettingvariantResponsebean withMessage(String message) { this.message = message;return this; }


    public String getSellingPrice() { return sellingPrice; }

    public void setSellingPrice(String sellingPrice) { this.sellingPrice = sellingPrice; }


}
