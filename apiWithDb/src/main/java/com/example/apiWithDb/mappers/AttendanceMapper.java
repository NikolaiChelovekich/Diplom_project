package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.AttendanceDto;
import com.example.apiWithDb.dto.EmployeeDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import com.example.apiWithDb.entities.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceRecord toAttendanceRecord(AttendanceDto attendanceDto);

}