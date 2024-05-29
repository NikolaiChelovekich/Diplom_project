package com.example.apiWithDb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AttendanceRequestDto {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;
    private LocalTime dailyTimeWorked;
}
