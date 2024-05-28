package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.service.CompanyService;
import com.example.apiWithDb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceimpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;


    public CompanyServiceimpl(CompanyRepository companyRepository, DepartmentRepository departmentRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
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
    public String updateCompany(Company company, Authentication authentication) {
        // Извлекаем аутентифицированного пользователя
        User user = userService.findUserByToken(authentication);

        // Извлекаем существующую компанию из базы данных по идентификатору компании
        Optional<Company> existingCompanyOpt = companyRepository.findById(company.getId());

        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();

            // Проверяем, является ли аутентифицированный пользователь владельцем компании
            if (existingCompany.getUser() != null && existingCompany.getUser().getId().equals(user.getId())) {
                // Обновляем данные компании
                company.setUser(user);
                companyRepository.save(company);
                return "Success";
               } else {
               // Возвращаем сообщение об ошибке, если пользователь не авторизован
                   throw new AppException("Not your company",HttpStatus.FORBIDDEN,403);
            }
        } else {
            // Возвращаем сообщение об ошибке, если компания не найдена
            throw  new AppException("Company not found",HttpStatus.NOT_FOUND,404);
        }
    }


    @Override
    public String deleteCompany(Long companyId, Authentication authentication) {

        User user = userService.findUserByToken(authentication);
        Optional<Company> existingCompanyOpt = companyRepository.findById(companyId);
        if (existingCompanyOpt.isEmpty()) {
            throw new AppException("Запрошенная компания не существует!", HttpStatus.NOT_FOUND, 404);
        }
        // Извлекаем компанию

        Company company = existingCompanyOpt.get();
        // Проверяем, принадлежит ли компания аутентифицированному пользователю
        if (!company.getUser().getId().equals(user.getId())) {
            throw new AppException("Вы не имеете прав на доступ к этой компании!", HttpStatus.FORBIDDEN, 403);
        }
        companyRepository.deleteCompanyByIdAndUserId(companyId, user.getId());
        return "Success";
    }

    @Override
    public Company getCompanyForAdmin(Long companyId, Authentication authentication) {
        User user = userService.findUserByToken(authentication);

        // Проверяем, существует ли компания с заданным идентификатором
        Optional<Company> existingCompanyOpt = companyRepository.findById(companyId);
        if (existingCompanyOpt.isEmpty()) {
            throw new AppException("Запрошенная компания не существует!", HttpStatus.NOT_FOUND, 404);
        }
        // Извлекаем компанию
        Company company = existingCompanyOpt.get();
        // Проверяем, принадлежит ли компания аутентифицированному пользователю
        if (!company.getUser().getId().equals(user.getId())) {
            throw new AppException("Вы не имеете прав на доступ к этой компании!", HttpStatus.FORBIDDEN, 403);
        }

        return company;
    }

    @Override
    public Company getCompanyForEmployee(Authentication authentication) {
        User user = userService.findUserByToken(authentication);


        // Проверяем, существует ли компания с заданным идентификатором
        Optional<Company> existingCompanyOpt = companyRepository.findCompanyByEmployeeLogin(user.getLogin());
        if (existingCompanyOpt.isEmpty()) {
            throw new AppException("Запрошенная компания не существует!", HttpStatus.NOT_FOUND, 404);
        }
        // Извлекаем компанию
        return  existingCompanyOpt.get();
        // Проверяем, принадлежит ли компания аутентифицированному пользователю

    }


    @Override
    public List<Company> getAllCompanies(Authentication authentication) {
        User user = userService.findUserByToken(authentication);

        return companyRepository.findAllCompaniesByUserId(user.getId());

    }
}
