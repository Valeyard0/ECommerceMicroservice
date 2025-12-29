package com.example.OrderService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private BigDecimal totalAmount;
    private String userId;

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addCartItem(@NotNull CartItem cartItem){
        cartItem.setCart(this);
        cartItems.add(cartItem);
    }

    public void deleteCartItem(@NotNull CartItem cartItem){
        cartItem.setCart(null);
        cartItems.remove(cartItem);
    }
}
