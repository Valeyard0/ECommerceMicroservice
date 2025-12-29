package com.example.ProductService.Event.Listener;

import com.example.ProductService.Entity.Product;
import com.example.ProductService.Event.Model.OrderCreatedEvent;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.ResourceNotFound;
import com.example.ProductService.Payload.DTO.OrderItemDetail;
import com.example.ProductService.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class InventoryUpdateListener {
    private final ProductRepository productRepository;

    @Bean
    public Consumer<OrderCreatedEvent> inventoryUpdates() {
        return this::handleOrderCreated;
    }

    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("<- Received Order Created Event. Decreasing inventory for Order: " + event.getOrderId());
        for (OrderItemDetail itemDetail : event.getOrderItemDetail()) {
            long productId;
            try {
                productId = Long.parseLong(itemDetail.getProductId());
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Invalid Product ID format: " + itemDetail.getProductId());
                continue;
            }
            Long orderedQuantity = itemDetail.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new ResourceNotFound("Product does not found"));

            if (product == null) {
                System.err.println("WARN: Product not found for inventory update (ID: " + productId + "). Skipping.");
                continue;
            }
            Long currentQuantity = product.getQuantity();
            long newQuantity = currentQuantity - orderedQuantity;

            if (newQuantity < 0) {
                System.err.println("ERROR: Insufficient inventory! Product ID: " + productId + ". Available: " + currentQuantity + ", Ordered: " + orderedQuantity);
                continue;
            }
            product.setQuantity(newQuantity);
            productRepository.save(product);

            System.out.println("-> Inventory updated for Product " + productId + ". New Qty: " + newQuantity);
        }
    }
}
