package com.growbiz.backend.Exception.exceptions.Service;

public class ServiceAlreadyExistsException extends RuntimeException {
    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
