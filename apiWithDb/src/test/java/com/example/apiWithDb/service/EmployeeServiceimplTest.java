package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.EmployeeDto;
import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.EmployeeMapper;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.repository.employeeRepository;
import com.example.apiWithDb.service.impl.EmployeeServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private employeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceimpl employeeService;

    private Department department;
    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        department = new Department();
        department.setId(1L);
        department.setName("Test Department");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartment(department);

        employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
    }

    @Test
    void createEmployee_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        String result = employeeService.createEmployee(employee, 1L);

        assertEquals("Success", result);
        verify(employeeRepository, times(1)).save(employee);
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void createEmployee_DepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.createEmployee(employee, 1L);
        });

        assertEquals("department not found", exception.getMessage());
          
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeDto.setFirstName("Jane");
        String result = employeeService.updateEmployee(employeeDto, 1L);

        assertEquals("Success", result);
        verify(employeeRepository, times(1)).save(employee);
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void updateEmployee_DepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.updateEmployee(employeeDto, 1L);
        });

        assertEquals("department not found", exception.getMessage());
          
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_EmployeeNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.updateEmployee(employeeDto, 1L);
        });

        assertEquals("Employee not found ", exception.getMessage());
          
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        String result = employeeService.deleteEmployee(1L, 1L);

        assertEquals("Success", result);
        verify(employeeRepository, times(1)).deleteByIdAndDepartmentId(1L, 1L);
    }

    @Test
    void deleteEmployee_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.deleteEmployee(1L, 1L);
        });

        assertEquals("Unknown employee", exception.getMessage());
          
        verify(employeeRepository, never()).deleteByIdAndDepartmentId(anyLong(), anyLong());
    }

    @Test
    void deleteEmployee_DepartmentNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.deleteEmployee(1L, 1L);
        });

        assertEquals("Unknown department", exception.getMessage());
          
        verify(employeeRepository, never()).deleteByIdAndDepartmentId(anyLong(), anyLong());
    }



    @Test
    void getEmployee_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.getEmployee(1L, 1L);
        });

        assertEquals("Unknown employee", exception.getMessage());

    }

    @Test
    void getEmployee_DepartmentNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.getEmployee(1L, 1L);
        });

        assertEquals("Unknown department", exception.getMessage());
    }


    @Test
    void getAllEmployees_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findAllByDepartmentId(1L)).thenReturn(List.of(employee));
        when(employeeMapper.toEmployeeDtoList(List.of(employee))).thenReturn(List.of(employeeDto));

        List<EmployeeDto> result = employeeService.getAllEmployees(1L);

        assertEquals(1, result.size());
        assertEquals(employeeDto, result.get(0));
    }

    @Test
    void getAllEmployees_DepartmentNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.getAllEmployees(1L);
        });

        assertEquals("Unknown department", exception.getMessage());
          
    }

    @Test
    void toSignUpDto_Success() {
        SignUpDto signUpDto = employeeService.toSignUpDto(employee);

        assertEquals("John", signUpDto.getFirstName());
        assertEquals("Doe", signUpDto.getLastName());
        assertEquals(employee.getCountry(), signUpDto.getCountry());
        assertEquals(employee.getLogin(), signUpDto.getLogin());
        assertEquals(employee.getPassword(), signUpDto.getPassword());
    }

    @Test
    void findById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findById(1L);

        assertEquals(employee, result);
    }

    @Test
    void findById_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            employeeService.findById(1L);
        });

        assertEquals("Employee not found", exception.getMessage());

    }
}
