package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.AttendanceDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.AttendanceMapper;
import com.example.apiWithDb.repository.AttendaceRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.repository.employeeRepository;
import com.example.apiWithDb.service.AttendanceService;
import com.example.apiWithDb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceimpl implements AttendanceService {

    private final AttendaceRepository attendaceRepository;
    private final employeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;
    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceimpl(AttendaceRepository attendaceRepository, employeeRepository employeeRepository, DepartmentRepository departmentRepository, UserService userService, AttendanceMapper attendanceMapper) {
        this.attendaceRepository = attendaceRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public String createAttendanceRecord(AttendanceDto attendanceDto, Authentication authentication) {
        Employee employee =   employeeRepository.findByLogin(userService.findUserByToken(authentication).getLogin())
                .orElseThrow(() -> new AppException("Employee not found ", HttpStatus.NOT_FOUND,404));

        if (attendanceDto.getRecordDate().isAfter(LocalDate.now()))
            throw new AppException("Attendance date cannot be in the future", HttpStatus.BAD_REQUEST, 400);

       if(attendaceRepository.findByDateAndEmployeeId(attendanceDto.getRecordDate(), employee.getId()).isPresent()) {
            throw new AppException("AttendanceRecord already exists", HttpStatus.CONFLICT, 409);
        }
        AttendanceRecord attendanceRecord = attendanceMapper.toAttendanceRecord(attendanceDto);
        attendanceRecord.setEmployee(employee);
        attendaceRepository.save(attendanceRecord);
        return "Success";
    }

    @Override
    public String updateAttendanceRecord(AttendanceDto attendanceDto,Authentication authentication) {
        Employee employee =   employeeRepository.findByLogin(userService.findUserByToken(authentication).getLogin())
                .orElseThrow(() -> new AppException("Employee not found ", HttpStatus.NOT_FOUND,404));

        if (attendanceDto.getRecordDate().isAfter(LocalDate.now()))
            throw new AppException("Attendance date cannot be in the future", HttpStatus.BAD_REQUEST, 400);

        if(attendaceRepository.findByDateAndEmployeeId(attendanceDto.getRecordDate(), employee.getId()).isPresent()) {
            throw new AppException("AttendanceRecord already exists", HttpStatus.CONFLICT, 409);
        }
        AttendanceRecord attendanceRecord = attendanceMapper.toAttendanceRecord(attendanceDto);
        attendanceRecord.setEmployee(employee);
        attendaceRepository.save(attendanceRecord);
        return "Success";
    }

    @Override
    public String deleteAttendanceRecord(LocalDate attendanceDate,Authentication authentication) {
        Employee employee =   employeeRepository.findByLogin(userService.findUserByToken(authentication).getLogin())
                .orElseThrow(() -> new AppException("Employee not found ", HttpStatus.NOT_FOUND,404));

        if (attendanceDate.isAfter(LocalDate.now()))
            throw new AppException("Attendance date cannot be in the future", HttpStatus.BAD_REQUEST, 400);

        AttendanceRecord attendanceRecord = attendaceRepository.findByDateAndEmployeeId(attendanceDate,employee.getId())
                        .orElseThrow(() -> new AppException("AttendanceRecord not found",HttpStatus.NOT_FOUND,404));
        attendaceRepository.delete(attendanceRecord);
        return "Success";
    }

    @Override
    public AttendanceRecord getAttendanceRecord(LocalDate attendanceDate,Authentication authentication) {
        Employee employee =   employeeRepository.findByLogin(userService.findUserByToken(authentication).getLogin())
                .orElseThrow(() -> new AppException("Employee not found ", HttpStatus.NOT_FOUND,404));

        if (attendanceDate.isAfter(LocalDate.now()))
            throw new AppException("Attendance date cannot be in the future", HttpStatus.BAD_REQUEST, 400);

       return attendaceRepository.findByDateAndEmployeeId(attendanceDate,employee.getId())
                .orElseThrow(() -> new AppException("AttendanceRecord not found",HttpStatus.NOT_FOUND,404));
    }

    @Override
    public List<AttendanceRecord> getAllDepartmentAttendanceRecords(Long departmentId, LocalDate attendanceDate) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException("Department not found", HttpStatus.NOT_FOUND,404));
        return attendaceRepository.findAttendanceRecordsByDepartmentIdAndDate(departmentId,attendanceDate);
    }
}
