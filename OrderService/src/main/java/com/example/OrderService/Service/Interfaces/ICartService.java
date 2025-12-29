package com.example.OrderService.Service.Interfaces;

import com.example.OrderService.Payload.CartPayload.CartRequest;
import com.example.OrderService.Payload.CartPayload.DTO.CartDTO;

import java.util.List;

public interface ICartService {
    String createCart(String authToken,String userId, CartRequest cartRequest);
    CartDTO removeProductFromCart(String authToken,String userId, CartRequest cartRequest);
    CartDTO gatherCart(String userId);
    List<CartDTO> gatherAllCarts();
    CartDTO updateProductQuantityFromCart(String authToken,String userId, CartRequest cartRequest);
}
