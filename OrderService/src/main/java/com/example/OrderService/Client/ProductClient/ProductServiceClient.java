package com.example.OrderService.Client.ProductClient;

import com.example.OrderService.Payload.ProductPayload.ProductDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface ProductServiceClient {
    @GetExchange("/api/v1/products/{productId}")
    Optional<ProductDTO> gatherProductByProductId(@RequestHeader("Authorization")String authToken, @PathVariable Long productId);
}
