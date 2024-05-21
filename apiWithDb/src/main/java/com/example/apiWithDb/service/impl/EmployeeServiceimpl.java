package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.repository.employeeRepository;
import com.example.apiWithDb.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceimpl implements EmployeeService {

    private final employeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceimpl(employeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public String createEmployee(Employee employee, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException("department not found",HttpStatus.NOT_FOUND,404));
        employee.setDepartment(department);
        employeeRepository.save(employee);

        return "Success";
    }

    @Override
    public String updateEmployee(Employee employee, Long departmentId) {
        employeeRepository.save(employee);
        return "Success";
    }

    @Override
    public String deleteEmployee(Long employeeId, Long departmentId) {
        if(employeeRepository.findById(employeeId).isEmpty())
            throw new AppException("Unknown employee", HttpStatus.NOT_FOUND,404);

        if(departmentRepository.findById(departmentId).isEmpty())
            throw new AppException("Unknown department", HttpStatus.NOT_FOUND,404);

        employeeRepository.deleteByIdAndDepartmentId(employeeId,departmentId);
        return "Success";
    }

    @Override
    public Employee getEmployee(Long employeeId, Long departmentId) {
        if(employeeRepository.findById(employeeId).isEmpty())
            throw new AppException("Unknown employee", HttpStatus.NOT_FOUND,404);

        if(employeeRepository.findById(employeeId).isEmpty())
            throw new AppException("Unknown department", HttpStatus.NOT_FOUND,404);

        return employeeRepository.findByIdAndDepartmentId(employeeId,departmentId);
    }

    @Override
    public List<Employee> getAllEmployees( Long departmentId) {

        if(departmentRepository.findById(departmentId).isEmpty())
            throw new AppException("Unknown department", HttpStatus.NOT_FOUND,404);

        return employeeRepository.findAllByDepartmentId(departmentId);
    }

    @Override
    public SignUpDto toSignUpDto(Employee employee) {
       SignUpDto signUpDto = SignUpDto.builder()
               .firstName(employee.getFirstName())
               .lastName(employee.getLastName())
               .country(employee.getCountry())
               .role("EMPLOYEE")
               .login(employee.getLogin())
               .password(employee.getPassword())
               .build();

       return signUpDto;
    }
}
