package com.feeling.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> errorHandler(
            IllegalArgumentException exception,
            WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                "Illegal Argument.",
                exception.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
