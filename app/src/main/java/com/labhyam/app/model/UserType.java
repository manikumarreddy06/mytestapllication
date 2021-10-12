package com.labhyam.app.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserType implements Serializable
{

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userType")
    @Expose
    private String userType;
    private final static long serialVersionUID = 7137291628715491841L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
