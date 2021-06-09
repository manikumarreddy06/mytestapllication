package com.myproject.myapplication;

import android.content.Context;

import com.myproject.myapplication.model.ProductVariant;

import java.util.ArrayList;
import java.util.List;

public class ProductUtils {

    private static ProductUtils mInstance = null;


    private static boolean outOrderTypeFlag;
    private static List<ProductVariant> list=new ArrayList<ProductVariant>();
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
            list.add(p);
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
}
