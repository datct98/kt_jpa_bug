package com.example.kt_jpa.controller;

import com.example.kt_jpa.model.dto.OrderDTO;
import com.example.kt_jpa.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO){

        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }
}
