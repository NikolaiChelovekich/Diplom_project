package com.example.apiWithDb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Long Id;
    private String firstName;
    private String LastName;
    private String login;
    private String token;

}
