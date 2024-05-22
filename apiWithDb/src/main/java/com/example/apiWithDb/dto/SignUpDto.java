package com.example.apiWithDb.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Login is mandatory")
    private String login;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @JsonIgnore
    private String role;

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and setters
}
