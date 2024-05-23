package com.example.apiWithDb.dto;

import com.example.apiWithDb.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeeDto {
    private Long  id;
    private String firstName;
    private String lastName;
    private String employeePhoto;
    @JsonProperty("Country") // Указываем, что это поле должно маппиться на поле "Country" в сущности
    private String country;
}