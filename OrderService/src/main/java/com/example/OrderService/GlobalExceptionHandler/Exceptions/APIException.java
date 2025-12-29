package com.example.OrderService.GlobalExceptionHandler.Exceptions;

public class APIException extends RuntimeException{
    public APIException(String message){
        super(message);
    }
}
