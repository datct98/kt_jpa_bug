package com.example.kt_jpa.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderDTO {
    @NotEmpty(message = "Shipper Id must not be null")
    private int shipperId;
    private CustomerDTO customer;
    @NotEmpty(message = "Address can't be null")
    private String address;

    //@DateTimeFormat(fallbackPatterns = "yyyy-MM-dd hh:mm")
    private Date estimateTime;
    private Set<ProductDTO> products;
    /*private String status;
    private double price;*/
}
