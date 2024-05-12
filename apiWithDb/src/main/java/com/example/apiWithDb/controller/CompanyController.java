package com.example.apiWithDb.controller;

import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.UserMapper;
import com.example.apiWithDb.repository.UserRepository;
import com.example.apiWithDb.response.ResponseHandler;
import com.example.apiWithDb.service.CompanyService;
import com.example.apiWithDb.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    public CompanyService CompanyService;
    public UserService userService;


    public CompanyController(CompanyService CompanyService, UserService userService)
    {
        this.CompanyService = CompanyService;
        this.userService = userService;

    }

    @GetMapping()
    public ResponseEntity<Object> getCompanyDetails()
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, CompanyService.getAllCompanys());
    }

    @GetMapping("{CompanyId}")
    public ResponseEntity<Object> getCompanyDetails(@PathVariable("CompanyId") String CompanyId)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, CompanyService.getCompany(CompanyId));
    }

    @PostMapping
    public String CreateCompanyDetails(@RequestBody Company company, Authentication authentication) {
        User user = userService.findUserByToken(authentication);
        company.setUser(user);
        CompanyService.createCompany(company);
        return "Company Created!";
    }


    @PutMapping
    public String UpdateCompanyDetails(@RequestBody  Company Company)
    {
        CompanyService.updateCompany(Company);
        return "Company Updated!";
    }

    @DeleteMapping("{CompanyId}")
    public String deleteCompany(@PathVariable("CompanyId") Integer CompanyId)
    {
        CompanyService.deleteCompany(CompanyId);
        return "Company Deleted!";
    }
}
