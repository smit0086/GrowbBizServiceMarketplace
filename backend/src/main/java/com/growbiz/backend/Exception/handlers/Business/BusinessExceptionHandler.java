package com.growbiz.backend.Exception.handlers.Business;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.Business.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Business.BusinessNotFoundException;
import com.growbiz.backend.Exception.models.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleBusinessNotFoundException(BusinessNotFoundException businessNotFoundException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.BUSINESS_NOT_FOUND.getValue(),
                businessNotFoundException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleBusinessNotFoundException(BusinessAlreadyExistsException businessAlreadyExistsException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.BUSINESS_ALREADY_EXIST.getValue(),
                businessAlreadyExistsException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
