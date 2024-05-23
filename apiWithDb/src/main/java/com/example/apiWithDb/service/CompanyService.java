package com.example.apiWithDb.service;

import com.example.apiWithDb.entities.Company;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CompanyService {

    public String createCompany(Company company,Authentication authentication);
    public String updateCompany(Company company,Authentication authentication);
    public String deleteCompany(Integer companyId,Authentication authentication);
    public Company getCompanyForAdmin(Integer companyId,Authentication authentication);
    public Company getCompanyForEmployee(Authentication authentication);
    default Company getCompany(String CompanyId) {
        return null;
    }

    public List<Company> getAllCompanies(Authentication authentication);
}
