package com.example.apiWithDb.service;

import com.example.apiWithDb.entities.Company;

import java.util.List;

public interface CompanyService {

    public String createCompany(Company company);
    public String updateCompany(Company company);
    public String deleteCompany(Integer companyId);
    public Company getCompany(Integer companyId);

    default Company getCompany(String CompanyId) {
        return null;
    }

    public List<Company> getAllCompanys();
}
