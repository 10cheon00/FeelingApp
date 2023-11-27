package com.feeling.app.user.exception;

public class ErrorResponse {
    private final String message;
    private final Object response;

    public ErrorResponse(String message, Object response) {
        this.message = message;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public Object getResponse() {
        return response;
    }
}
