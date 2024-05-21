package com.example.apiWithDb.dto;

import com.example.apiWithDb.utils.Role;
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
    private String lastName;
    private String login;
    private String country;
    private String token;
    private Role role;

}
