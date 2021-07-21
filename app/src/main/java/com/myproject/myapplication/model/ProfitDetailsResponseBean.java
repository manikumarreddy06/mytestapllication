package com.myproject.myapplication.model;


import java.io.Serializable;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class ProfitDetailsResponseBean implements Serializable
{

    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("salesInfo")
    @Expose
    private List<SalesInfo> salesInfo = null;
    @SerializedName("sumofQuatity")
    @Expose
    private String sumofQuatity;
    @SerializedName("sumofProfit")
    @Expose
    private String sumofProfit;
    @SerializedName("sumofSales")
    @Expose
    private String sumofSales;

    private final static long serialVersionUID = 5790103676674001020L;

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public List<SalesInfo> getSalesInfo() {
        return salesInfo;
    }

    public void setSalesInfo(List<SalesInfo> salesInfo) {
        this.salesInfo = salesInfo;
    }

    public String getSumofQuatity() {
        return sumofQuatity;
    }

    public void setSumofQuatity(String sumofQuatity) {
        this.sumofQuatity = sumofQuatity;
    }

    public String getSumofProfit() {
        return sumofProfit;
    }

    public void setSumofProfit(String sumofProfit) {
        this.sumofProfit = sumofProfit;
    }

    public String getSumofSales() {
        return sumofSales;
    }

    public void setSumofSales(String sumofSales) {
        this.sumofSales = sumofSales;
    }
}
