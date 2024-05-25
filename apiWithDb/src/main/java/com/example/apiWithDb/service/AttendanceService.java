package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.AttendanceDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    public String createAttendanceRecord(AttendanceDto attendanceDto, Authentication authentication);
    public String updateAttendanceRecord(AttendanceDto attendanceDto,  Authentication authentication);
    public String deleteAttendanceRecord(LocalDate attendanceDate,Authentication authentication);
    public AttendanceRecord getAttendanceRecord(LocalDate attendanceDate,Authentication authentication);
    List<AttendanceRecord> getAllDepartmentAttendanceRecords(Long departmentId, LocalDate attendanceDate);
}
