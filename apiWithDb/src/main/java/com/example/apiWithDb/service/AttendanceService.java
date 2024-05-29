package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.dto.AttendanceResponseDto;
import com.example.apiWithDb.dto.MonthWorkTimeDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {

    public String createAttendanceRecord(AttendanceRequestDto attendanceDto, Authentication authentication);
    public String updateAttendanceRecord(AttendanceRequestDto attendanceDto, Authentication authentication);
    public String deleteAttendanceRecord(LocalDate attendanceDate,Authentication authentication);
    public AttendanceResponseDto getAttendanceRecord(LocalDate attendanceDate, Authentication authentication);
    List<AttendanceResponseDto> getAllDepartmentAttendanceRecords(Long departmentId, LocalDate attendanceDate, Authentication authentication);


    Duration getTotalWorkedTimeForMonth(Authentication authentication, LocalDate month, Long employeeId);
}
