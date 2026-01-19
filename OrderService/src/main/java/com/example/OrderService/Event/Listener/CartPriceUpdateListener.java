package com.example.OrderService.Event.Listener;

import com.example.OrderService.Entity.Cart;
import com.example.OrderService.Entity.CartItem;
import com.example.OrderService.Event.Model.ProductPriceUpdatedEvent;
import com.example.OrderService.Repository.CartItemRepository;
import com.example.OrderService.Repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class CartPriceUpdateListener {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Bean
    public Consumer<ProductPriceUpdatedEvent> productPriceUpdates() {
        return event -> {
            try {
                handleProductPriceUpdate(event);
            } catch (Exception e) {
                System.err.println("!!! Error processing price update event for Product ID " + event.getProductId() + ": " + e.getMessage());
            }
        };
    }

    @Transactional
    public void handleProductPriceUpdate(ProductPriceUpdatedEvent event) {
        String productId = event.getProductId().toString();
        BigDecimal newPrice = event.getNewPrice();

        System.out.println("<- Received Price Update Event: Product ID=" + productId + ", New Price=" + newPrice);

        List<CartItem> cartItems = cartItemRepository.findCartItemsByProductId(productId);

        if (cartItems.isEmpty()) {
            System.out.println("<- No carts found to update for product ID: " + productId);
            return;
        }

        for (CartItem item : cartItems) {
            Cart cart = item.getCart();

            // 1. Eski toplam tutarı sepetten düş
            BigDecimal oldItemTotal = item.getPrice();
            cart.setTotalAmount(cart.getTotalAmount().subtract(oldItemTotal));

            // 2. Yeni sepet öğesi fiyatını hesapla ve güncelle
            Long quantity = item.getQuantity();
            BigDecimal newItemTotal = newPrice.multiply(new BigDecimal(quantity));
            item.setPrice(newItemTotal);

            // 3. Yeni toplam tutarı sepete ekle
            cart.setTotalAmount(cart.getTotalAmount().add(newItemTotal));

            System.out.println("[Cart DB] Cart ID " + cart.getCartId() + " updated. New Total: " + cart.getTotalAmount());

            cartItemRepository.save(item);
            cartRepository.save(cart);
        }
    }
}