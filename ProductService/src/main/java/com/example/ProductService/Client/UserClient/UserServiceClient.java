package com.example.ProductService.Client.UserClient;


import com.example.ProductService.Payload.DTO.UserDTO.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface UserServiceClient {
    @GetExchange("api/v1/users/username/{username}")
    Optional<UserDTO> findUserByUsername(@PathVariable String username);
    @GetExchange("api/v1/users/{userId}")
    Optional<UserDTO> findUserById(@PathVariable Long userId);
}
