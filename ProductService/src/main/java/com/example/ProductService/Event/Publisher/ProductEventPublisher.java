package com.example.ProductService.Event.Publisher;

import com.example.ProductService.Event.Model.ProductPriceUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ProductEventPublisher {
    private final StreamBridge streamBridge;
    private static final String TOPIC_NAME = "productPriceUpdates";

    public ProductEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishProductPriceUpdatedEvent(Long productId, BigDecimal newPrice){
        ProductPriceUpdatedEvent event = new ProductPriceUpdatedEvent(productId,newPrice, LocalDateTime.now());
        System.out.println("-> Publishing event: " + event.toString());
        boolean result = streamBridge.send(TOPIC_NAME,event);
        if(result)
            System.out.println("-> Event successfully published to " + TOPIC_NAME);
        else
            System.err.println("-> Failed to publish event!");
    }
}
