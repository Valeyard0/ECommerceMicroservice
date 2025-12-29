package com.example.OrderService.Client.UserClient;

import com.example.OrderService.Payload.UserPayload.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface UserServiceClient {
    @GetExchange("api/v1/users/{userId}")
    Optional<UserDTO> findUserById(@PathVariable Long userId);
    @GetExchange("api/v1/users/username/{username}")
    Optional<UserDTO> findUserByUsername(@PathVariable String username);
}
