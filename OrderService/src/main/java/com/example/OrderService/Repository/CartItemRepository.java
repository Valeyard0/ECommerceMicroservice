package com.example.OrderService.Repository;

import com.example.OrderService.Entity.Cart;
import com.example.OrderService.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findCartItemByUserIdAndProductId(String userId, String productId);
    List<CartItem> findCartItemsByProductId(String productId);
    List<CartItem> findCartItemsByCart(Cart cart);
}