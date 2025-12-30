package com.example.demo.Car;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoModel {
    String brand;
    String model;
    double price;
    int topSpeed;
    String image;
    String fuelType;
    String description;

    public InfoModel(String brand,String model, double price, int topSpeed, String image, String fuelType, String description) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.topSpeed = topSpeed;
        this.image = image;
        this.fuelType = fuelType;
        this.description = description;
    }
}
