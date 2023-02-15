package com.example.kt_jpa.service;

import com.example.kt_jpa.model.dto.OrderDTO;
import com.example.kt_jpa.model.entities.Order;

public interface OrderService {
    Order createOrder(OrderDTO dto);
    String updateOrder(OrderDTO dto, String id);

}
