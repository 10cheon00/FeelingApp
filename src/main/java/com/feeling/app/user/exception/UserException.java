package com.feeling.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalUserFormException.class)
    protected ResponseEntity<ErrorResponse> errorHandler(
            IllegalUserFormException exception,
            WebRequest request
    ) {

        return new ResponseEntity<>(
                new ErrorResponse(
                        "user creation failed",
                        exception.getIllegalFields()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
