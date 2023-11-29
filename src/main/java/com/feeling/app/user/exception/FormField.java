package com.feeling.app.user.exception;

public class FormField {
    private final String name;
    private final String value;

    public FormField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
