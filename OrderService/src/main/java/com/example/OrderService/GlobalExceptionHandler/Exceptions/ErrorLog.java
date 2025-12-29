package com.example.OrderService.GlobalExceptionHandler.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    private LocalDateTime localDateTime;
    private String message;
    private HttpStatusCode httpStatus;
}
