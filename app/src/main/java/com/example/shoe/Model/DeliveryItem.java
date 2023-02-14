package com.example.shoe.Model;

public class DeliveryItem {
    String deliveryCode;
    String deliveryDate;
    String deliveryHour;
    String carCode;
    String status;

    public DeliveryItem() {
    }

    public DeliveryItem(String deliveryCode, String deliveryDate, String deliveryHour, String carCode, String status) {
        this.deliveryCode = deliveryCode;
        this.deliveryDate = deliveryDate;
        this.deliveryHour = deliveryHour;
        this.carCode = carCode;
        this.status = status;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryHour() {
        return deliveryHour;
    }

    public void setDeliveryHour(String deliveryHour) {
        this.deliveryHour = deliveryHour;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
