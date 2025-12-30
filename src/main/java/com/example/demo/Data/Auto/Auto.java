package com.example.demo.Data.Auto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "autos")
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double price;

    @Column(name = "top_speed")
    private int topSpeed;

    @Column(nullable = false)
    private String image;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(nullable = false)
    private String description;

    public Auto() {}

    public Auto(String brand, String model, double price, int topSpeed, String image, String fuelType, String description) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.topSpeed = topSpeed;
        this.image = image;
        this.fuelType = fuelType;
        this.description = description;
    }
}
