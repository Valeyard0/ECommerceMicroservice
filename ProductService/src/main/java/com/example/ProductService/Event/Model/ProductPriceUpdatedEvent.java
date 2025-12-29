package com.example.ProductService.Event.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceUpdatedEvent {
    private Long productId;
    private BigDecimal newPrice;
    private LocalDateTime localDateTime;
}
