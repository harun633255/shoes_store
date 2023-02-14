package com.example.shoe.Model;

public class Vehicle {
    String carcode,cartype,capacity,status,image;

    public Vehicle() {
    }

    public Vehicle(String carcode, String cartype, String capacity, String status, String image) {
        this.carcode = carcode;
        this.cartype = cartype;
        this.capacity = capacity;
        this.status = status;
        this.image = image;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
