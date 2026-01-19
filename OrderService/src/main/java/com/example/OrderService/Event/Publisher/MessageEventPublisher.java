package com.example.OrderService.Event.Publisher;

import com.example.OrderService.Client.UserClient.UserServiceClient;
import com.example.OrderService.Event.Model.MessageEvent;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import com.example.OrderService.Payload.UserPayload.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MessageEventPublisher {
    private final StreamBridge streamBridge;
    private final UserServiceClient userServiceClient;
    private final static String TOPIC_BINDING_NAME = "messageCreatedEvents-out-0";

    public void publishMessageCreatedEvent(Long orderId, String userId, BigDecimal totalPrice) {
        UserDTO user = userServiceClient.findUserById(Long.valueOf(userId)).orElseThrow(() -> new ResourceNotFoundException("User nto found:" + userId));
        MessageEvent messageEvent = new MessageEvent(orderId, user.getEmail(), totalPrice);
        boolean result = streamBridge.send(TOPIC_BINDING_NAME, messageEvent);
        if (result) {
            System.out.println("-> Event successfully published to " + TOPIC_BINDING_NAME + " topic.");
        } else {
            System.err.println("-> FAILED to publish Order Created event!");
        }
    }
}
