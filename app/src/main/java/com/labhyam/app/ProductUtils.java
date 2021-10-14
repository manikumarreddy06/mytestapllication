package com.labhyam.app;

import android.content.Context;

import com.labhyam.app.model.CategoryItem;
import com.labhyam.app.model.ProductVariant;

import java.util.ArrayList;
import java.util.List;

public class ProductUtils {

    private static ProductUtils mInstance = null;


    private static boolean outOrderTypeFlag;
    private static List<ProductVariant> list=new ArrayList<ProductVariant>();
    private static List<CategoryItem> CategoryList=new ArrayList<CategoryItem>();


    public static ProductUtils instance(Context context) {
        if (mInstance == null)
            mInstance = new ProductUtils();

        return mInstance;
    }

    public void addProduct(ProductVariant p){

        if(list==null){
            list=new ArrayList<>();
            list.add(p);
        }
        else{
            float updatedQty=0;
            boolean itemExists=false;
            for(ProductVariant product:list){
                if(product.getVariantId()==p.getVariantId()){
                    itemExists=true;
                    updatedQty=product.getQuantity()+p.getQuantity();
                    product.setQuantity(updatedQty);
                    product.setProcPrice(p.getProcPrice());
                    product.setSellingPrice(p.getSellingPrice());
                    break;
                }

            }
            if(!itemExists){
                list.add(p);
            }
        }

    }

    public void updateQty(ProductVariant p){

       for(ProductVariant product:list){
           if(product.getVariantId()==p.getVariantId()){
               product.setQuantity(p.getQuantity());
           }

       }


    }

    public List<ProductVariant> getProductList(){

       return list;

    }

    public void clear(){
        if(list!=null) {
            list.clear();
            outOrderTypeFlag=false;
        }
    }


    public  boolean isOutOrderTypeFlag() {
        return outOrderTypeFlag;
    }

    public void setOutOrderTypeFlag(boolean outOrderTypeFlag) {
        ProductUtils.outOrderTypeFlag = outOrderTypeFlag;
    }

    public static List<CategoryItem> getCategoryList() {
        return CategoryList;
    }

    public static void setCategoryList(List<CategoryItem> categoryList) {
        CategoryList = categoryList;
    }

    public float getTotalProcumentPrice(){
        float totalPrice=0l;
        for(ProductVariant product:list){
            totalPrice=totalPrice+(product.getProcPrice()*product.getQuantity());
        }
        return totalPrice;
    }
    public float getTotalSellingPrice(){
        float totalPrice=0l;
        for(ProductVariant product:list){
            totalPrice=totalPrice+(product.getSellingPrice()*product.getQuantity());
        }
        return totalPrice;
    }
}
