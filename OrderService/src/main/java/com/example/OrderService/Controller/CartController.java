package com.example.OrderService.Controller;

import com.example.OrderService.Payload.CartPayload.CartRequest;
import com.example.OrderService.Payload.CartPayload.DTO.CartDTO;
import com.example.OrderService.Service.Interfaces.ICartService;
import com.example.OrderService.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;
    private final AuthUtil authUtil;

    @PostMapping("/cart")
    public ResponseEntity<String> createCart(@RequestHeader("Authorization")String authToken,@RequestBody CartRequest cartRequest){
        String userId = authUtil.findUserIdByLoggedUser();
        String message = cartService.createCart(authToken,userId,cartRequest);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<CartDTO> removeProductFromCart(@RequestHeader("Authorization") String authToken, @RequestBody CartRequest cartRequest){
        String userId = authUtil.findUserIdByLoggedUser();
        CartDTO cartDTO = cartService.removeProductFromCart(authToken,userId,cartRequest);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartDTO> gatherCart(){
        String userId = authUtil.findUserIdByLoggedUser();
        CartDTO cartDTO = cartService.gatherCart(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> gatherAllCarts(){
        List<CartDTO> cartList = cartService.gatherAllCarts();
        return ResponseEntity.ok(cartList);
    }

    @PutMapping("/cart")
    public ResponseEntity<CartDTO> updateProductQuantityFromCart(@RequestHeader("Authorization")String authToken,@RequestBody CartRequest cartRequest){
        String userId = authUtil.findUserIdByLoggedUser();
        CartDTO cartDTO = cartService.updateProductQuantityFromCart(authToken,userId,cartRequest);
        return ResponseEntity.ok(cartDTO);
    }
}