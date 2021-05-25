package com.myproject.myapplication.inward;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddProduct implements Serializable
{

    @SerializedName("productId")
    @Expose
    private long productId;
    @SerializedName("variantId")
    @Expose
    private long variantId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("procPrice")
    @Expose
    private long procPrice;
    @SerializedName("sellingPrice")
    @Expose
    private long sellingPrice;
    @SerializedName("storeId")
    @Expose
    private long storeId;
    @SerializedName("quantity")
    @Expose
    private long quantity;
    private final static long serialVersionUID = 4478176112159785282L;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVariantId() {
        return variantId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getProcPrice() {
        return procPrice;
    }

    public void setProcPrice(long procPrice) {
        this.procPrice = procPrice;
    }

    public long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}