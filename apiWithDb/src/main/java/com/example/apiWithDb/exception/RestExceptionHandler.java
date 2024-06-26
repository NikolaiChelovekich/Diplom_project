package com.example.apiWithDb.exception;

import com.example.apiWithDb.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex)
    {
        return ResponseEntity.status(ex.getCode())
                .body(ErrorDto.builder().error(ex.getMessage()).status(ex.getStatudCode()).build());
    }

}
