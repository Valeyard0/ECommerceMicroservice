package com.example.ProductService.Event.Model;

import com.example.ProductService.Payload.DTO.OrderItemDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private String userId;
    private List<OrderItemDetail> orderItemDetail = new ArrayList<>();
}
