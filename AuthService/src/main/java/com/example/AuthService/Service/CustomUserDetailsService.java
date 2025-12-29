package com.example.AuthService.Service;

import com.example.AuthService.Client.UserClientService.UserServiceClient;
import com.example.AuthService.Payload.UserPayload.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userServiceClient.findUserByUsername(username);
        return new CustomUserDetails(userDTO);
    }
}
