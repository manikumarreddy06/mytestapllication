package com.labhyam.app.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfitResponseBean implements Serializable
{

    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("salesAmount")
    @Expose
    private String salesAmount;
    @SerializedName("profitAmount")
    @Expose
    private String profitAmount;

    @SerializedName("monthlySales")
    @Expose
    private String monthlySales;
    @SerializedName("monthlyProfit")
    @Expose
    private String monthlyProfit;
    

    private final static long serialVersionUID = -4941892137231056136L;

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        this.profitAmount = profitAmount;
    }


    public String getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(String monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getMonthlyProfit() {
        return monthlyProfit;
    }

    public void setMonthlyProfit(String monthlyProfit) {
        this.monthlyProfit = monthlyProfit;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}

