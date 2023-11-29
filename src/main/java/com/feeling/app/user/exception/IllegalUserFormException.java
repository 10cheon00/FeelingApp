package com.feeling.app.user.exception;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class IllegalUserFormException extends Exception {
    private final List<FormField> illegalFields;

    public IllegalUserFormException(List<FormField> illegalFields) {
        super();
        this.illegalFields = illegalFields;
    }

    public List<FormField> getIllegalFields() {
        return illegalFields;
    }
}