package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.entities.Employee;

import java.util.List;

public interface EmployeeService {

    public String createEmployee(Employee employee, Long departmentId);
    public String updateEmployee(Employee employee, Long departmentId);
    public String deleteEmployee(Long employeeId, Long departmentId);
    public com.example.apiWithDb.entities.Employee getEmployee(Long employeeId, Long departmentId);
    public List<com.example.apiWithDb.entities.Employee> getAllEmployees( Long departmentId);
    public SignUpDto toSignUpDto(Employee employee);
}
