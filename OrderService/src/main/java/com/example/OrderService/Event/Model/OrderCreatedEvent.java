package com.example.OrderService.Event.Model;

import com.example.OrderService.Payload.OrderPayload.OrderItemDetail;
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
