package com.growbiz.backend.Exception.handlers.Category;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.Category.CategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Category.CategoryNotFoundException;
import com.growbiz.backend.RequestResponse.Exception.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CategoriesExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.CATEGORY_ALREADY_EXIST.getValue(),
                exception.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException exception) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.CATEGORY_NOT_FOUND.getValue(),
                exception.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
