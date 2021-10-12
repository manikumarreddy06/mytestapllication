package com.labhyam.app.model;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NegativeValue implements Serializable
{

    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("variantId")
    @Expose
    private String variantId;
    private final static long serialVersionUID = 7690721163140839818L;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

}