package com.growbiz.backend.Exception.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
