package com.example.OrderService.Util;

import com.example.OrderService.Client.UserClient.UserServiceClient;
import com.example.OrderService.Payload.UserPayload.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final UserServiceClient userServiceClient;

    public String findUserIdByLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO principal = (UserDTO) authentication.getPrincipal();
        UserDTO user = userServiceClient.findUserById(principal.getUserId()).orElseThrow(()-> new RuntimeException("Username not found"));
        return user.getUserId().toString();
    }
}
