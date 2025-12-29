package com.example.ProductService.GlobalExceptionHandler.CustomExceptionHandlers;

import com.example.ProductService.GlobalExceptionHandler.Exceptions.AlreadyExistException;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.ErrorLog;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorLog> httpMessageNotReadableException(HttpMessageNotReadableException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorLog> alreadyExistsException(AlreadyExistException ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorLog> resourceNotFoundException(ResourceNotFound ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog,HttpStatus.BAD_REQUEST);
    }

}
