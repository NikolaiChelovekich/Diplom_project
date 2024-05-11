package com.example.apiWithDb.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AppException extends RuntimeException {

    private final HttpStatus code;
    private final int statudCode;
    public AppException(String message, HttpStatus code, int statusCode) {
        super(message);
        this.code = code;
        this.statudCode = statusCode;
    }


}
