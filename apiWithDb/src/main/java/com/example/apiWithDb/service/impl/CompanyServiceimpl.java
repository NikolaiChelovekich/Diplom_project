package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceimpl implements CompanyService {

    private final CompanyRepository companyRepository;


    public CompanyServiceimpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public String createCompany(Company company) {
        companyRepository.save(company);
        return "Success";
    }

    @Override
    public String updateCompany(Company company) {
        companyRepository.save(company);
        return "Success";
    }

    @Override
    public String deleteCompany(Integer companyId) {
        if(companyRepository.findById(companyId).isEmpty())
            throw new RuntimeException("Запрошенная компания не существует!");
        companyRepository.deleteById(companyId);
        return "Success";
    }

    @Override
    public Company getCompany(Integer companyId) {
        if(companyRepository.findById(companyId).isEmpty())
            throw new RuntimeException("Запрошенная компания не существует!");
        return companyRepository.findById(companyId).get();
    }

    @Override
    public List<Company> getAllCompanys() {
        return companyRepository.findAll();
    }
}
