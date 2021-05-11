
package com.myproject.myapplication.model;

import java.io.Serializable;

public class User implements Serializable
{

    private long id;
    private String username;
    private Object password;
    private String userPhoneNumber;
    private String cityId;
    private Object cityName;
    private Object userTypeId;
    private Object userTypeName;
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

    public void setPassword(Object password) {
        this.password = password;
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

    public void setCityName(Object cityName) {
        this.cityName = cityName;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Object getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(Object userTypeName) {
        this.userTypeName = userTypeName;
    }

}
