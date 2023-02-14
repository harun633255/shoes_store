package com.example.shoe.Model;

public class OrderItem {
    String orderCode;
    String oderDate;
    String clientCode;
    String timeOfOrder;
    String orderSum;
    String status;
    PaymentDetails paymentDetails;

    public OrderItem() {
    }

    public OrderItem(String orderCode, String oderDate, String clientCode, String timeOfOrder, String orderSum, String status, PaymentDetails paymentDetails) {
        this.orderCode = orderCode;
        this.oderDate = oderDate;
        this.clientCode = clientCode;
        this.timeOfOrder = timeOfOrder;
        this.orderSum = orderSum;
        this.status = status;
        this.paymentDetails = paymentDetails;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOderDate() {
        return oderDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public String getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(String orderSum) {
        this.orderSum = orderSum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
