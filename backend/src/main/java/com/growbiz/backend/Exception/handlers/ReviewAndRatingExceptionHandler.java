package com.growbiz.backend.Exception.handlers;

import com.growbiz.backend.Exception.exceptions.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.BusinessNotFoundException;
import com.growbiz.backend.Exception.exceptions.ReviewAndRatingAlreadyExists;
import com.growbiz.backend.Exception.exceptions.ReviewAndRatingNotFoundException;
import com.growbiz.backend.Exception.models.BasicErrorResponse;
import com.growbiz.backend.Exception.models.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

public class ReviewAndRatingExceptionHandler {
    @ExceptionHandler(ReviewAndRatingAlreadyExists.class)
    public ResponseEntity<BasicErrorResponse> handleReviewAndRatingAlreadyExists(ReviewAndRatingAlreadyExists reviewAndRatingAlreadyExists) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.REVIEW_RATING_ALREADY_EXISTS.getValue(),
                reviewAndRatingAlreadyExists.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewAndRatingNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleReviewAndRatingNotFoundException(ReviewAndRatingNotFoundException reviewAndRatingNotFoundException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.REVIEW_RATING_NOT_FOUND.getValue(),
                reviewAndRatingNotFoundException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
