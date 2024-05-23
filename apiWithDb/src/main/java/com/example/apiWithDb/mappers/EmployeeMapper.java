package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.EmployeeDto;
import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toEmployeeDto(Employee employee);

    List<EmployeeDto> toEmployeeDtoList(List<Employee> employees);
}