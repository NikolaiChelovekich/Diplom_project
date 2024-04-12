package com.example.apiWithDb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StuffMemberExceptionHandler {

    @ExceptionHandler(value = {StuffMemberNotFoundException.class})
    public ResponseEntity<Object> handleStuffMemberNotFoundException
            (StuffMemberNotFoundException stuffMemberNotFoundException)
    {
        StuffMemberException stuffMemberException = new StuffMemberException(
                stuffMemberNotFoundException.getMessage(),
                stuffMemberNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(stuffMemberException,HttpStatus.NOT_FOUND);
    }

}
