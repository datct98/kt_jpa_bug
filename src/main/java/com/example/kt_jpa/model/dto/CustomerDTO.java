package com.example.kt_jpa.model.dto;

import com.example.kt_jpa.model.entities.Order;
import com.example.kt_jpa.model.entities.Vote;
import com.example.kt_jpa.model.entities.Wallet;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CustomerDTO {
    private String name;
    @NotEmpty(message = "Customer's phone cant be null")
    private String phone;

    private Wallet wallet;

    /*private Set<Order> orders;
    private Set<Vote> votes;*/
}
