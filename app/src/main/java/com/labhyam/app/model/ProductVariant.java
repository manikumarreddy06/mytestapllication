
package com.labhyam.app.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductVariant implements Serializable
{

    @SerializedName("variantId")
    @Expose
    private long variantId;
    @SerializedName("productVariantName")
    @Expose
    private String productVariantName;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("unit_type")
    @Expose
    private String unitType;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("parent_category")
    @Expose
    private String parentCategory;
    @SerializedName("product_family")
    @Expose
    private Object productFamily;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Object getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(Object productFamily) {
        this.productFamily = productFamily;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
