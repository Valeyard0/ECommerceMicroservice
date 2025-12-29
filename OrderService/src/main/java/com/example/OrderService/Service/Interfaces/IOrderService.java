package com.example.OrderService.Service.Interfaces;

import com.example.OrderService.Payload.OrderPayload.OrderDTO;

public interface IOrderService {
    OrderDTO createOrder(String userId);
}
