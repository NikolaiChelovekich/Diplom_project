package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.EmployeeDto;
import com.example.apiWithDb.dto.EmployeeDtoNoLogin;
import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public String createEmployee(Employee employee, Long departmentId);
    public String updateEmployee(EmployeeDtoNoLogin employeeDto, Long departmentId);
    public String deleteEmployee(Long employeeId, Long departmentId);
    public EmployeeDto getEmployee(Long employeeId, Long departmentId);
    public List<EmployeeDto> getAllEmployees( Long departmentId);
    public SignUpDto toSignUpDto(Employee employee);
    public Employee findById(Long employeeId);
    public Employee findByLogin(String login);
}
