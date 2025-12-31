package com.example.demo.Data.Auto;

import com.example.demo.Data.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private String imagePath;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AutoStatus status = AutoStatus.NOT_FOR_SALE;

    public Auto() {}

    public Auto(String brand, String model, double price, int topSpeed, String imagePath, String fuelType, String description, AutoStatus status) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.topSpeed = topSpeed;
        this.imagePath = imagePath;
        this.fuelType = fuelType;
        this.description = description;
        this.status = status;
    }
}
