package com.labhyam.app.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesInfo implements Serializable
{

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("countOfSale")
    @Expose
    private String countOfSale;
    @SerializedName("profit")
    @Expose
    private String profit;
    private final static long serialVersionUID = 2423540807763384545L;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCountOfSale() {
        return countOfSale;
    }

    public void setCountOfSale(String countOfSale) {
        this.countOfSale = countOfSale;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

}