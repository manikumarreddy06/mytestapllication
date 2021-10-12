
package com.labhyam.app.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponseBean implements Serializable
{

    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categoryItems")
    @Expose
    private List<CategoryItem> categoryItems = null;
    private final static long serialVersionUID = -7900180724853661196L;

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

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

}
