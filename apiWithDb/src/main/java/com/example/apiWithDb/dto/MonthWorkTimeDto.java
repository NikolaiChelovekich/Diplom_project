package com.example.apiWithDb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class MonthWorkTimeDto {
    private Long employeeId;
    private LocalTime totalWorkedTimeInMonth;
}