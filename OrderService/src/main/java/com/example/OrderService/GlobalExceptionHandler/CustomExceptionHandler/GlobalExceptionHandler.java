package com.example.OrderService.GlobalExceptionHandler.CustomExceptionHandler;

import com.example.OrderService.GlobalExceptionHandler.Exceptions.APIException;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.AlreadyExistsException;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.ErrorLog;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorLog> resourceNotFoundHandler(ResourceNotFoundException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorLog> alreadyExistsException(AlreadyExistsException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorLog> httpClientErrorException(HttpClientErrorException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),ex.getStatusCode());
        return new ResponseEntity<>(errorLog,ex.getStatusCode());
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorLog> apiException(APIException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog,HttpStatus.BAD_REQUEST);
    }
}
