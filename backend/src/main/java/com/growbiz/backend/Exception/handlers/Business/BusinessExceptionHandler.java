package com.growbiz.backend.Exception.handlers.Business;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.Business.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Business.BusinessNotFoundException;
import com.growbiz.backend.RequestResponse.Exception.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleBusinessNotFoundException(BusinessNotFoundException exception) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.BUSINESS_NOT_FOUND.getValue(),
                exception.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleBusinessNotFoundException(BusinessAlreadyExistsException exception) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.BUSINESS_ALREADY_EXIST.getValue(),
                exception.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
