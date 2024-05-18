package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceimpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentServiceimpl(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public String createDepartment(Department department, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException("Company not found",HttpStatus.NOT_FOUND,404));

        department.setCompany(company);

        departmentRepository.save(department);
        return "Success";

    }

    @Override
    public String updateDepartment(Department department, Long companyId) {
        // Найти компанию по ID
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException("Company not found",HttpStatus.NOT_FOUND,404));

        // Найти отдел по его ID
       departmentRepository.findById(department.getId())
                .orElseThrow(() -> new AppException("Department not found",HttpStatus.NOT_FOUND,404));

        // Обновить данные отдела
        department.setName(department.getName());
        department.setCompany(company);

        // Сохранить обновленный отдел в базе данных
        departmentRepository.save(department);

        // Вернуть какой-то результат, например, сообщение об успешном обновлении
        return "Department updated successfully";
    }

    @Override
    public String deleteDepartment(Long departmentId, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException("Company not found",HttpStatus.NOT_FOUND,404));

        // Найти отдел по его ID
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException("Department not found",HttpStatus.NOT_FOUND,404));

        // Проверить, принадлежит ли отдел к указанной компании
        if (!department.getCompany().equals(company)) {
            throw new AppException("Department does not belong to the specified company",HttpStatus.BAD_REQUEST,400);
        }

        // Удалить отдел из базы данных
        departmentRepository.delete(department);

        // Вернуть какой-то результат, например, сообщение об успешном удалении
        return "Department deleted successfully";
    }

    @Override
    public Department getDepartment(Long departmentId, Long companyId) {
        // Найти компанию по ID
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException("Company not found",HttpStatus.NOT_FOUND,404));

        // Найти отдел по его ID и компании
        Department department =  departmentRepository.findByIdAndCompany(departmentId, company)
                .orElseThrow(() -> new AppException("Department not found",HttpStatus.NOT_FOUND,404));

        return department;
    }


    @Override
    public List<Department> getAllDepartments(Long companyId) {
        // Найти компанию по ID
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException("Company not found",HttpStatus.NOT_FOUND,404));

        // Получить все отделы для указанной компании
        return departmentRepository.findByCompany(company);
    }

}
