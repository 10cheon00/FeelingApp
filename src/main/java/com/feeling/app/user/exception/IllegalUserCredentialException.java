package com.feeling.app.user.exception;

import com.feeling.app.user.entity.User;

public class IllegalUserCredentialException extends Exception {
    private final User user;

    public IllegalUserCredentialException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
