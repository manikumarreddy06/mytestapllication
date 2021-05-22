package com.myproject.myapplication.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails implements Serializable
{

    @SerializedName("id")
    @Expose
    private long id;

    public ProductDetails(String productName) {
        this.productName = productName;
    }

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("categoryId")
    @Expose
    private Object categoryId;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 5813106815197538236L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}