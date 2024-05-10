package com.example.apiWithDb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {
    private String message;
    private int statusCode;


}
