package com.example.OrderService.Payload.CartPayload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private String productId;
    private String userId;
    private BigDecimal price;
    private Long quantity;
}