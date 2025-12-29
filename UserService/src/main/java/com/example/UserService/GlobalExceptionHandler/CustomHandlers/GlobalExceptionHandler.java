package com.example.UserService.GlobalExceptionHandler.CustomHandlers;


import com.example.UserService.GlobalExceptionHandler.Exceptions.AlreadyExistsException;
import com.example.UserService.GlobalExceptionHandler.Exceptions.ErrorLog;
import com.example.UserService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class, AlreadyExistsException.class})
    public ResponseEntity<ErrorLog> badRequestException(Exception ex){
        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(),ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorLog,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("message", "Argument is not valid.");
        errorDetails.put("violations", validationErrors);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
