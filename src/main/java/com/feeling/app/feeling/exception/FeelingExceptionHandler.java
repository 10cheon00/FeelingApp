package com.feeling.app.feeling.exception;

import com.feeling.app.feeling.exception.DuplicatedDateFeelingException;
import com.feeling.app.user.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FeelingExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicatedDateFeelingException.class)
    protected ResponseEntity<ErrorResponse> errorHandler(
            DuplicatedDateFeelingException exception,
            WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                "Feeling data is duplicated.",
                exception.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
