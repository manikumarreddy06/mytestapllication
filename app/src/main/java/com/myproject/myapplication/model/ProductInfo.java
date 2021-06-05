
package com.myproject.myapplication.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductInfo implements Serializable
{

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("variantId")
    @Expose
    private String variantId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("variantName")
    @Expose
    private String variantName;


    @SerializedName("procPrice")
    @Expose
    private long procPrice;

    @SerializedName("sellingPrice")
    @Expose
    private long sellingPrice;

    @SerializedName("quantity")
    @Expose
    private long quantity;

    public ProductInfo(long quantity) {
        this.quantity = quantity;
    }



    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
