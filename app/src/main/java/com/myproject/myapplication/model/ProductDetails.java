
package com.myproject.myapplication.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails implements Serializable
{

    @SerializedName("id")
    @Expose
    private long id;
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
    @SerializedName("productVariants")
    @Expose
    private List<ProductVariant> productVariants = null;
    private final static long serialVersionUID = -395865367225957669L;

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

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }


    public ProductDetails(String productName) {
        this.productName = productName;
    }
}
