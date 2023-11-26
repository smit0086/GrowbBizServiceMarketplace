package com.growbiz.backend.Exception.exceptions.Service;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
