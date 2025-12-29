package com.example.ProductService.GlobalExceptionHandler.Exceptions;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String message) {
        super(message);
    }
}
