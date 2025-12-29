package com.example.OrderService.Payload.OrderPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDetail {
    private String productId;
    private Long quantity;
}