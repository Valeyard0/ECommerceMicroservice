package com.example.AuthService.Service;

import com.example.AuthService.Client.UserClientService.UserServiceClient;
import com.example.AuthService.Payload.Request.LoginRequest;
import com.example.AuthService.Payload.Request.RegisterRequest;
import com.example.AuthService.Payload.Request.TokenDTO;
import com.example.AuthService.Service.Interface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final JwtGenerationService jwtGenerationService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    public TokenDTO login(LoginRequest request) {
        System.out.println("Username:"+request.getUsername() +" Password:" + passwordEncoder.encode(request.getPassword()));
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authenticate.isAuthenticated())
            return TokenDTO.builder()
                    .token(jwtGenerationService.generateToken(request.getUsername()))
                    .build();
        else throw new RuntimeException("Wrong credentials");
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        userServiceClient.createUser(registerRequest);
        return "User successfully created";
    }
}