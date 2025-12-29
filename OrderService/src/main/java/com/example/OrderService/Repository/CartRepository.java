package com.example.OrderService.Repository;

import com.example.OrderService.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByUserId( String userId);

}
