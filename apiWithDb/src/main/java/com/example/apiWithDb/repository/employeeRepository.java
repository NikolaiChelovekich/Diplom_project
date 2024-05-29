package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface employeeRepository extends JpaRepository<Employee, Long> {

    Employee findByIdAndDepartmentId(Long Id, Long departmentId);
    @Transactional
    @Modifying
    void deleteByIdAndDepartmentId(Long Id, Long departmentId);
    List<Employee> findAllByDepartmentId(Long departmentId);


    Optional<Employee> findByLogin(String login);
}
