package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.service.CompanyService;
import com.example.apiWithDb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceimpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;


    public CompanyServiceimpl(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    @Override
    public String createCompany(Company company,Authentication authentication) {
        User user = userService.findUserByToken(authentication);
        company.setUser(user);
        companyRepository.save(company);
        return "Success";
    }

    @Override
    public String updateCompany(Company company,Authentication authentication) {
        User user = userService.findUserByToken(authentication);
        company.setUser(user);
        companyRepository.save(company);
        return "Success";
    }

    @Override
    public String deleteCompany(Integer companyId, Authentication authentication) {
        User user = userService.findUserByToken(authentication);
        if(companyRepository.findById(companyId).isEmpty())
            throw new AppException("Запрошенная компания не существует!",HttpStatus.NOT_FOUND,404);
        companyRepository.deleteCompanyByIdAndUserId(companyId,user.getId());
        return "Success";
    }

    @Override
    public Company getCompany(Integer companyId,Authentication authentication) {
        User user = userService.findUserByToken(authentication);

        if(companyRepository.findById(companyId).isEmpty())
            throw new AppException("Запрошенная компания не существует!",HttpStatus.NOT_FOUND,404);
        return companyRepository.findByIdAndUserId(companyId,user.getId());
    }

    @Override
    public List<Company> getAllCompanies(Authentication authentication) {
        User user = userService.findUserByToken(authentication);

        return companyRepository.findAllCompaniesByUserId(user.getId());

    }
}
