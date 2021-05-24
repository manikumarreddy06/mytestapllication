
package com.myproject.myapplication.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductVariant implements Serializable
{

    @SerializedName("productId")
    @Expose
    private long productId;
    @SerializedName("variantId")
    @Expose
    private long variantId;
    @SerializedName("productVariantName")
    @Expose
    private String productVariantName;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("procPrice")
    @Expose
    private double procPrice;
    @SerializedName("sellingPrice")
    @Expose
    private double sellingPrice;
    @SerializedName("storeId")
    @Expose
    private Object storeId;
    @SerializedName("quantity")
    @Expose
    private long quantity;
    private final static long serialVersionUID = -4447632388529275673L;

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

    public String getProductVariantName() {
        return productVariantName;
    }

    public void setProductVariantName(String productVariantName) {
        this.productVariantName = productVariantName;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public double getProcPrice() {
        return procPrice;
    }

    public void setProcPrice(double procPrice) {
        this.procPrice = procPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Object getStoreId() {
        return storeId;
    }

    public void setStoreId(Object storeId) {
        this.storeId = storeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
