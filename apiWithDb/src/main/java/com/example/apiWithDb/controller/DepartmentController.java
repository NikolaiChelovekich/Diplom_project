package com.example.apiWithDb.controller;


import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.service.DepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/{companyId}/departments")
@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartmentDetails(@PathVariable("companyId") Long companyId) {
        return departmentService.getAllDepartments(companyId);
    }

    @GetMapping("/{departmentId}")
    public Department getDepartmentDetails(@PathVariable("companyId") Long companyId, @PathVariable Long departmentId) {
        return departmentService.getDepartment(departmentId, companyId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createDepartmentDetails(@RequestBody Department department, @PathVariable("companyId") Long companyId) {
        departmentService.createDepartment(department, companyId);
        return "Department created";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public String updateDepartmentDetails(@RequestBody Department department, @PathVariable("companyId") Long companyId) {
        departmentService.updateDepartment(department, companyId);
        return "Department updated";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{departmentId}")
    public String deleteDepartmentDetails(@PathVariable("companyId") Long companyId, @PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId, companyId);
        return "Department deleted";
    }
}