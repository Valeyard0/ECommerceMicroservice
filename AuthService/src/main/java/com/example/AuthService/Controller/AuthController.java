package com.example.AuthService.Controller;

import com.example.AuthService.Payload.Request.LoginRequest;
import com.example.AuthService.Payload.Request.RegisterRequest;
import com.example.AuthService.Payload.Request.TokenDTO;
import com.example.AuthService.Service.Interface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest loginRequest){
        TokenDTO tokenDTO = authService.login(loginRequest);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        String message = authService.register(registerRequest);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }
}