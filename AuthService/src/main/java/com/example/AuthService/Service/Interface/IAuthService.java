package com.example.AuthService.Service.Interface;

import com.example.AuthService.Payload.Request.LoginRequest;
import com.example.AuthService.Payload.Request.RegisterRequest;
import com.example.AuthService.Payload.Request.TokenDTO;

public interface IAuthService {
    TokenDTO login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
}
