package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface employeeRepository extends JpaRepository<Employee, Long> {

    Employee findByIdAndDepartmentId(Long Id, Long departmentId);
    void deleteByIdAndDepartmentId(Long Id, Long departmentId);
    List<Employee> findAllByDepartmentId(Long departmentId);


}
