
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
    private String description;
    @SerializedName("price")
    @Expose
    private long price;
    @SerializedName("procPrice")
    @Expose
    private long procPrice;
    @SerializedName("sellingPrice")
    @Expose
    private long sellingPrice;
    @SerializedName("storeId")
    @Expose
    private String storeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
