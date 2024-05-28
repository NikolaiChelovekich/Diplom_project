package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.EmployeeDto;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    }

    @Test
    void testToEmployeeDto() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmployeePhoto("photo.jpg");
        employee.setCountry("USA");
        employee.setRole(Role.USER);
        employee.setLogin("johndoe");
        employee.setPassword("password123");
        employee.setDepartment(department);

        EmployeeDto employeeDto = employeeMapper.toEmployeeDto(employee);

        assertNotNull(employeeDto);
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getEmployeePhoto(), employeeDto.getEmployeePhoto());
        assertEquals(employee.getCountry(), employeeDto.getCountry());
    }

    @Test
    void testToEmployeeDtoList() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmployeePhoto("photo1.jpg");
        employee1.setCountry("USA");
        employee1.setRole(Role.USER);
        employee1.setLogin("johndoe");
        employee1.setPassword("password123");
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmployeePhoto("photo2.jpg");
        employee2.setCountry("Canada");
        employee2.setRole(Role.USER);
        employee2.setLogin("janesmith");
        employee2.setPassword("password456");
        employee2.setDepartment(department);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        List<EmployeeDto> employeeDtos = employeeMapper.toEmployeeDtoList(employees);

        assertNotNull(employeeDtos);
        assertEquals(2, employeeDtos.size());

        EmployeeDto employeeDto1 = employeeDtos.get(0);
        assertEquals(employee1.getId(), employeeDto1.getId());
        assertEquals(employee1.getFirstName(), employeeDto1.getFirstName());
        assertEquals(employee1.getLastName(), employeeDto1.getLastName());
        assertEquals(employee1.getEmployeePhoto(), employeeDto1.getEmployeePhoto());
        assertEquals(employee1.getCountry(), employeeDto1.getCountry());

        EmployeeDto employeeDto2 = employeeDtos.get(1);
        assertEquals(employee2.getId(), employeeDto2.getId());
        assertEquals(employee2.getFirstName(), employeeDto2.getFirstName());
        assertEquals(employee2.getLastName(), employeeDto2.getLastName());
        assertEquals(employee2.getEmployeePhoto(), employeeDto2.getEmployeePhoto());
        assertEquals(employee2.getCountry(), employeeDto2.getCountry());
    }
}