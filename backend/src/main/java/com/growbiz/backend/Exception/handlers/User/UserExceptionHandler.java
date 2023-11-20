package com.growbiz.backend.Exception.handlers.User;

import com.growbiz.backend.Enums.ErrorMessages;
import com.growbiz.backend.Exception.exceptions.User.UserAlreadyExistsException;
import com.growbiz.backend.RequestResponse.Exception.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BasicErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.USER_ALREADY_EXIST.getValue(),
                userAlreadyExistsException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.USERNAME_NOT_FOUND.getValue(),
                usernameNotFoundException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BasicErrorResponse> handleBadCredentialsException(BadCredentialsException badCredentialsException) {

        return new ResponseEntity<>(new BasicErrorResponse(ErrorMessages.USERNAME_PASSWORD_INCORRECT.getValue(),
                badCredentialsException.getMessage(),
                new Date()), HttpStatus.BAD_REQUEST);
    }
}
