
package com.labhyam.app.model;

import java.io.Serializable;

public class LoginResponseBean implements Serializable
{

    private boolean isvalid;
    private String message;
    private User user;
    private Object productDetails;
    private Object productVariants;
    private final static long serialVersionUID = -7609654427336791554L;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Object getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Object productDetails) {
        this.productDetails = productDetails;
    }

    public Object getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(Object productVariants) {
        this.productVariants = productVariants;
    }

}
