package com.example.AuthService.Payload.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String token;
}
