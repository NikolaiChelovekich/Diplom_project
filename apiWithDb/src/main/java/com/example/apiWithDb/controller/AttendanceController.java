package com.example.apiWithDb.controller;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.response.ResponseHandler;
import com.example.apiWithDb.service.AttendanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/company/{companyId}/departments/{departmentId}/attendance")
@SecurityRequirement(name = "bearerAuth")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/all/{attendanceDate}")
    public ResponseEntity<Object> getAllAttendanceRecordsDetails(@PathVariable("departmentId") Long departmentId, @PathVariable LocalDate attendanceDate, Authentication authentication)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, attendanceService.getAllDepartmentAttendanceRecords(departmentId,attendanceDate,authentication));
    }

    @GetMapping("/{attendanceDate}")
    public ResponseEntity<Object> getAttendanceRecordsDetails(@PathVariable LocalDate attendanceDate,Authentication authentication)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, attendanceService.getAttendanceRecord(attendanceDate,authentication));
    }

    @DeleteMapping("/{attendanceDate}")
    public ResponseEntity<Object> deleteAttendanceRecordDetails(@PathVariable LocalDate attendanceDate,Authentication authentication)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, attendanceService.deleteAttendanceRecord(attendanceDate,authentication));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public String createAttendanceRecordDetails(@RequestBody AttendanceRequestDto attendanceDto, Authentication authentication) {
        attendanceService.createAttendanceRecord(attendanceDto, authentication);
        return "AttendanceRecord created";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public String updateAttendanceRecordDetails(@RequestBody AttendanceRequestDto attendanceDto, Authentication authentication) {
        attendanceService.updateAttendanceRecord(attendanceDto, authentication);
        return "AttendanceRecord created";
    }

//    @GetMapping("/month/{attendanceDate}")// Пример прав доступа, можете изменить
//    public ResponseEntity<Duration> getMonthlyWorkTimeReport(@PathVariable("departmentId") Long departmentId,@PathVariable LocalDate attendanceDate, Authentication authentication) {
//        Duration time = attendanceService.getTotalWorkedTimeForMonth(authentication,attendanceDate);
//        return ResponseEntity.status(HttpStatus.OK).body(time);
//    }

}
