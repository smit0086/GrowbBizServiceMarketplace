package com.growbiz.backend.Exception.handlers.Service;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.Service.ServiceAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Service.ServiceNotFoundException;
import com.growbiz.backend.Exception.models.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleServiceNotFoundException(ServiceNotFoundException serviceNotFoundException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.SERVICE_NOT_FOUND.getValue(),
                serviceNotFoundException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleServiceAlreadyExists(ServiceAlreadyExistsException serviceAlreadyExistsException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.SERVICE_ALREADY_EXISTS.getValue(),
                serviceAlreadyExistsException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
