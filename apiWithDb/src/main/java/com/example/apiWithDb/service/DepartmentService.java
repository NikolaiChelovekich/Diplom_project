package com.example.apiWithDb.service;

import com.example.apiWithDb.entities.Department;
import java.util.List;

public interface DepartmentService {

    public String createDepartment(Department department, Long companyId);
    public String updateDepartment(Department department, Long companyId);
    public String deleteDepartment(Long departmentId, Long companyId);
    public Department getDepartment(Long departmentId, Long companyId);

    List<Department> getAllDepartments(Long companyId);
}
