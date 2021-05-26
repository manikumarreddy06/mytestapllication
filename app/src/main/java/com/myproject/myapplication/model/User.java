
package com.myproject.myapplication.model;

import java.io.Serializable;

public class User implements Serializable
{

    private long id;
    private String username;
    private String password;
    private String userPhoneNumber;
    private String cityId;
    private String cityName;
    private String userTypeId;
    private String userTypeName;

    private String storeId;

    private String storeName;

    private final static long serialVersionUID = -4259411983342301927L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }


    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
