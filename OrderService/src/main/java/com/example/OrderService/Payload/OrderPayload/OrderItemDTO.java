package com.example.OrderService.Payload.OrderPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderItemDTO {
    private Long orderItemId;
    private Long quantity;
    private BigDecimal price;
}
