package com.labhyam.app.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse implements Serializable
{

    @SerializedName("isvalid")
    @Expose
    private boolean isvalid;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cityList")
    @Expose
    private List<City> cityList = null;


    @SerializedName("userTypes")
    @Expose
    private List<UserType> userTypes = null;

    @SerializedName("listOfStores")
    @Expose
    private List<Store> listOfStores = null;
    private final static long serialVersionUID = 4613383490004428039L;

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

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public List<Store> getListOfStores() {
        return listOfStores;
    }

    public void setListOfStores(List<Store> listOfStores) {
        this.listOfStores = listOfStores;
    }
}