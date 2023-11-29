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
                        "User creation failed",
                        exception.getIllegalFields()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalUserCredentialException.class)
    protected ResponseEntity<ErrorResponse> errorHandler(
            IllegalUserCredentialException exception,
            WebRequest request
    ){
        return new ResponseEntity<>(
                new ErrorResponse(
                        "User credential is not valid.",
                        exception.getUser()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotValidateJwtException.class)
    protected ResponseEntity<ErrorResponse> errorHandler(
            NotValidateJwtException exception,
            WebRequest request
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Jwt is not valid or expired.",
                        ""
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
