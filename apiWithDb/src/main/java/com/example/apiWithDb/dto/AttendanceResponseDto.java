package com.example.apiWithDb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AttendanceResponseDto {

    private Long id;

    private Date recordDate;

    private LocalTime dailyTimeWorked;
    private EmployeeDto employee;
}
