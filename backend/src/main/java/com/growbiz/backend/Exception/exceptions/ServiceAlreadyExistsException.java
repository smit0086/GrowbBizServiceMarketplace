package com.growbiz.backend.Exception.exceptions;

public class ServiceAlreadyExistsException extends RuntimeException{
    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
