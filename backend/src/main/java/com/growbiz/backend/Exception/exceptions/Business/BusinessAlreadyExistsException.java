package com.growbiz.backend.Exception.exceptions.Business;

public class BusinessAlreadyExistsException extends RuntimeException {
    public BusinessAlreadyExistsException(String message) {
        super(message);
    }

}
