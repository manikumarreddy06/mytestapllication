package com.myproject.myapplication.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailResponse implements Serializable
{

    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private Object user;
    @SerializedName("productDetails")
    @Expose
    private ProductDetails productDetails;
    private final static long serialVersionUID = 1286529609297930646L;

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

}
