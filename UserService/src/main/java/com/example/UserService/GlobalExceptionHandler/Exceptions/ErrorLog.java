package com.example.UserService.GlobalExceptionHandler.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    private LocalDateTime localDateTime;
    private String message;
    private HttpStatus httpStatus;
}