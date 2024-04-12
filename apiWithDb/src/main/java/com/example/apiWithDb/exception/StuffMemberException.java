package com.example.apiWithDb.exception;

import org.springframework.http.HttpStatus;

public class StuffMemberException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public StuffMemberException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
