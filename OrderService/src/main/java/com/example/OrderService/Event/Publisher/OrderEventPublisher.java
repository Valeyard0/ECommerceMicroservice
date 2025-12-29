package com.example.OrderService.Event.Publisher;

import com.example.OrderService.Event.Model.OrderCreatedEvent;
import com.example.OrderService.Payload.OrderPayload.OrderItemDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final StreamBridge streamBridge;
    private final static String TOPIC_BINDING_NAME = "orderCreatedEvents";

    public void publishOrderCreatedEvent(Long orderId, String userId, List<OrderItemDetail> orderItemDetails){
        OrderCreatedEvent event = new OrderCreatedEvent(orderId,userId,orderItemDetails);
        System.out.println("-> Publishing Order Created Event: Order ID " + orderId);
        System.out.println("Test order:"+event);
        boolean result = streamBridge.send(TOPIC_BINDING_NAME, event);
        if (result) {
            System.out.println("-> Event successfully published to " + TOPIC_BINDING_NAME + " topic.");
        } else {
            System.err.println("-> FAILED to publish Order Created event!");
        }
    }
}
