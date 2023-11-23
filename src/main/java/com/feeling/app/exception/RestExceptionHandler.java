package com.feeling.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> errorHandler(IllegalArgumentException exception, WebRequest request){
        String responseString = "Illegal Argument.";
        return handleExceptionInternal(
                exception,
                responseString,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }
}
