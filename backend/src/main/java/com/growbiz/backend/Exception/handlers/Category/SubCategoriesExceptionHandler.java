package com.growbiz.backend.Exception.handlers.Category;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.Category.SubCategoryAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Category.SubCategoryNotFoundException;
import com.growbiz.backend.Exception.models.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class SubCategoriesExceptionHandler {

    @ExceptionHandler(SubCategoryAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleSubCategoryAlreadyExistsException(SubCategoryAlreadyExistsException subCategoryAlreadyExistsException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.SUBCATEGORY_ALREADY_EXIST.getValue(),
                subCategoryAlreadyExistsException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleSubCategoryNotFoundException(SubCategoryNotFoundException subCategoryNotFoundException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.SUBCATEGORY_NOT_FOUND.getValue(),
                subCategoryNotFoundException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
