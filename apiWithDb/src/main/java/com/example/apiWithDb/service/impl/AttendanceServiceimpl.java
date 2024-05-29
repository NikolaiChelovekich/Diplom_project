package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.dto.AttendanceResponseDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.AttendanceMapper;
import com.example.apiWithDb.repository.AttendanceRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.repository.employeeRepository;
import com.example.apiWithDb.service.AttendanceService;
import com.example.apiWithDb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceimpl implements AttendanceService {

    private final AttendanceRepository attendaceRepository;
    private final employeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;
    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceimpl(AttendanceRepository attendaceRepository, employeeRepository employeeRepository, DepartmentRepository departmentRepository, UserService userService, AttendanceMapper attendanceMapper) {
        this.attendaceRepository = attendaceRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public String createAttendanceRecord(AttendanceRequestDto attendanceDto, Authentication authentication) {
        String login = userService.findUserByToken(authentication).getLogin();
        Employee employee =   employeeRepository.findByLogin(login)
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
    public String updateAttendanceRecord(AttendanceRequestDto attendanceDto, Authentication authentication) {
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
    public AttendanceResponseDto getAttendanceRecord(LocalDate attendanceDate, Authentication authentication) {
        Employee employee =   employeeRepository.findByLogin(userService.findUserByToken(authentication).getLogin())
                .orElseThrow(() -> new AppException("Employee not found ", HttpStatus.NOT_FOUND,404));

        if (attendanceDate.isAfter(LocalDate.now()))
            throw new AppException("Attendance date cannot be in the future", HttpStatus.BAD_REQUEST, 400);

       return attendanceMapper.toAttendanceResponseDto(attendaceRepository.findByDateAndEmployeeId(attendanceDate,employee.getId())
               .orElseThrow(() -> new AppException("AttendanceRecord not found",HttpStatus.NOT_FOUND,404)));
    }

    @Override
    public List<AttendanceResponseDto> getAllDepartmentAttendanceRecords(Long departmentId, LocalDate attendanceDate) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException("Department not found", HttpStatus.NOT_FOUND,404));
        return attendanceMapper.toAttendanceResponseDtoList(attendaceRepository.findAttendanceRecordsByDepartmentIdAndDate(departmentId,attendanceDate));
    }
}
