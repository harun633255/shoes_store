package com.example.shoe.Model;

public class DeliveryDetails {
    String deliveryCode;
    String orderCode;

    public DeliveryDetails() {
    }

    public DeliveryDetails(String deliveryCode, String orderCode) {
        this.deliveryCode = deliveryCode;
        this.orderCode = orderCode;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
