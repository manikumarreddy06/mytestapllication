package com.labhyam.app.inward;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddProduct implements Serializable
{


    @SerializedName("variantId")
    @Expose
    private long variantId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("procPrice")
    @Expose
    private float procPrice;
    @SerializedName("sellingPrice")
    @Expose
    private float sellingPrice;
    @SerializedName("storeId")
    @Expose
    private long storeId;
    @SerializedName("quantity")
    @Expose
    private float quantity;
    private final static long serialVersionUID = 4478176112159785282L;


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

    public float getProcPrice() {
        return procPrice;
    }

    public void setProcPrice(float procPrice) {
        this.procPrice = procPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

}