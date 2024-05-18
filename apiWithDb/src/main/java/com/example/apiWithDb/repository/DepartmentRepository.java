package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findByIdAndCompany(Long departmentId, Company company);

    List<Department> findByCompany(Company company);

    @Transactional
    @Modifying
    void deleteByCompanyId(Integer companyId);
}
