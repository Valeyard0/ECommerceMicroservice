package com.example.AuthService.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorLog> illegalStateException(IllegalStateException ex){
        ErrorLog errorLog = new ErrorLog(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorLog,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorLog> httpClientErrorException(HttpClientErrorException ex){
        ErrorLog errorLog = new ErrorLog(ex.getMessage(), LocalDateTime.now(), ex.getStatusCode());
        return new ResponseEntity<>(errorLog,ex.getStatusCode());
    }

}
