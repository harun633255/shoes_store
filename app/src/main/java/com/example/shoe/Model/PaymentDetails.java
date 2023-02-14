package com.example.shoe.Model;

import android.provider.ContactsContract;

public class PaymentDetails {
    String paymentMethod;
    String paymentDescription;
    String phoneNo;
    String email;

    public PaymentDetails(String paymentMethod, String paymentDescription, String phoneNo, String email) {
        this.paymentMethod = paymentMethod;
        this.paymentDescription = paymentDescription;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public PaymentDetails() {
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
