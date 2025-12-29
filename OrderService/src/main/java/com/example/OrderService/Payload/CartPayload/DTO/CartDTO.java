package com.example.OrderService.Payload.CartPayload.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartId;
    private BigDecimal totalAmount;
    private String userId;
    private List<CartItemDTO> cartItems = new ArrayList<>();
}
