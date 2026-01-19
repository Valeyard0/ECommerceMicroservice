package com.example.OrderService.Event.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEvent {
    private Long orderId;
    private String email;
    private BigDecimal totalPrice;
}
