package com.example.AuthService.Client.UserClientService;

import com.example.AuthService.Payload.Request.RegisterRequest;
import com.example.AuthService.Payload.UserPayload.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface UserServiceClient {
    @GetExchange("/api/v1/users/username/{username}")
    UserDTO findUserByUsername(@PathVariable String username);
    @PostExchange("/api/v1/users/createUser")
    String createUser(@RequestBody RegisterRequest request);
}
