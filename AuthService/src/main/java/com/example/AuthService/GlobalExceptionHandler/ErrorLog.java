package com.example.AuthService.GlobalExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    private String message;
    private LocalDateTime localDateTime;
    private HttpStatusCode httpStatus;
}
