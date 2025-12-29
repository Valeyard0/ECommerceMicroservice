package com.example.AuthService.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

@Component
@RequiredArgsConstructor
public class JwtGenerationService {
    private final CustomUserDetailsService customUserDetailsService;
    private static final String SECRET = "azI5ZjhzRzdkOVF4UDFtQzR0Ujh2WjN5TDZ3QjBuSHE=";

    public String generateToken(String username) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder().subject(userDetails.getUsername())
                .claim("roles", roles)
                .claim("userId",userDetails.getUserId())
                .issuer("auth-service")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}