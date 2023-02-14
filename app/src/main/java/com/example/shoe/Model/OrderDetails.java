package com.example.shoe.Model;

import java.util.ArrayList;

public class OrderDetails {
    String orderCode;
    String productCodes;

    public OrderDetails() {
    }

    public OrderDetails(String orderCode, String productCodes) {
        this.orderCode = orderCode;
        this.productCodes = productCodes;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    public String getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(String productCodes) {
        this.productCodes = productCodes;
    }

}
