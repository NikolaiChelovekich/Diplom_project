package com.example.apiWithDb.exception;

public class StuffMemberNotFoundException extends RuntimeException {

    public StuffMemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StuffMemberNotFoundException(String message) {
        super(message);
    }
}
