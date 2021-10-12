
package com.labhyam.app.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryItem implements Serializable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private List<ProductVariant> value = null;
    private final static long serialVersionUID = -2225582692427071729L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ProductVariant> getValue() {
        return value;
    }

    public void setValue(List<ProductVariant> value) {
        this.value = value;
    }

}
