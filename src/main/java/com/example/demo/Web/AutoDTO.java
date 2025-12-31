package com.example.demo.Web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AutoDTO {
    @NotBlank(message="Brand обов'язковий")
    private String brand;

    @NotBlank(message="Model обов'язковий")
    private String model;

    @Positive(message="Price має бути > 0")
    private double price;

    @Positive(message="TopSpeed має бути > 0")
    private int topSpeed;

    @NotNull(message="Image обов'язковий")
    private MultipartFile image;

    @NotBlank(message="FuelType обов'язковий")
    private String fuelType;

    @NotBlank(message="Description обов'язковий")
    private String description;
}
