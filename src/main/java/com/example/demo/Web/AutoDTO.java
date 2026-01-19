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

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @Positive(message = "Top speed must be greater than 0")
    private int topSpeed;

    @NotNull(message = "Image is required")
    private MultipartFile image;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @NotBlank(message = "Description is required")
    private String description;
}
