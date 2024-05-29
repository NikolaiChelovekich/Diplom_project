package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.dto.AttendanceResponseDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.entities.User;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<AttendanceResponseDto> getAllDepartmentAttendanceRecords(Long departmentId, LocalDate attendanceDate, Authentication authentication) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException("Department not found", HttpStatus.NOT_FOUND, 404));

        List<AttendanceRecord> attendanceRecords = attendaceRepository.findAttendanceRecordsByDepartmentIdAndDate(departmentId, attendanceDate);
        List<AttendanceResponseDto> attendanceResponseDtos = attendanceMapper.toAttendanceResponseDtoList(attendanceRecords);

        // Получаем список уникальных сотрудников из записей посещаемости
        Set<Long> employeeIds = attendanceRecords.stream()
                .map(record -> record.getEmployee().getId())
                .collect(Collectors.toSet());

        // Вычисляем и устанавливаем общее отработанное время за месяц для каждого сотрудника
        for (AttendanceResponseDto dto : attendanceResponseDtos) {
            Long employeeId = dto.getEmployee().getId();
            Duration totalWorkedTimeForMonth = getTotalWorkedTimeForMonth(authentication, attendanceDate, employeeId);
            dto.setHoursThisMonth(totalWorkedTimeForMonth.toString());
        }
        return attendanceResponseDtos;
    }

    public Duration getTotalWorkedTimeForMonth(Authentication authentication, LocalDate month, Long employeeId) {
        User user = userService.findUserByToken(authentication);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppException("Employee not found", HttpStatus.NOT_FOUND, 404));

        LocalDate firstDayOfMonth = month.withDayOfMonth(1);
        LocalDate lastDayOfMonth = month.withDayOfMonth(month.lengthOfMonth());

        List<AttendanceRecord> attendances = attendaceRepository.getAllAttendancesForDateRangeAndEmployee(firstDayOfMonth, lastDayOfMonth, employee.getId());

        long totalWorkedSeconds = 0;

        for (AttendanceRecord attendance : attendances) {
            totalWorkedSeconds += attendance.getDailyTimeWorked().toSecondOfDay();
        }
        return Duration.ofSeconds(totalWorkedSeconds);
    }




}
