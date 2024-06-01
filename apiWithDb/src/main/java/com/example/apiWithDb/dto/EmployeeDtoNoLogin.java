package com.example.apiWithDb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeeDtoNoLogin {
    private Long id;
    private String firstName;
    private String lastName;
    private String employeePhoto;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("position")
    private String position;
    @JsonProperty("birthDate")
    private Date birthDate;
}
