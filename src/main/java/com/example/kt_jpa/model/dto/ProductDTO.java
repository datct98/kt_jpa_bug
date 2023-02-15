package com.example.kt_jpa.model.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    @Min(value = 0, message = "Weight cant be less than 0")
    private double weight;
    @Min(value = 0, message = "Quantity cant be less than 0")
    private int quantity;
}
