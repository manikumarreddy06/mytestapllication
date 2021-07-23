package com.myproject.myapplication.model;

import java.io.Serializable;
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

}