package com.example.apiWithDb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CredentialsDto {
    @NotBlank(message = "Login is mandatory")
    private String login;
    @NotBlank(message = "Password name is mandatory")
    private String password;
}
