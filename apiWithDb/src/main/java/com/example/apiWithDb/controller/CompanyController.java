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
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Object> getCompanyDetails(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean isUser = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));

        Object data = isAdmin ? CompanyService.getAllCompanies(authentication)
                : isUser ? CompanyService.getCompanyForEmployee(authentication)
                : null;

        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, data);
    }

    @GetMapping("{CompanyId}")
    public ResponseEntity<Object> getCompanyDetailsForAdmin(@PathVariable("CompanyId") Long CompanyId,Authentication authentication) {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, CompanyService.getCompanyForAdmin(CompanyId,authentication));
    }
    

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String CreateCompanyDetails(@RequestBody Company company, Authentication authentication) {
        CompanyService.createCompany(company, authentication);
        return "Company Created!";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public String UpdateCompanyDetails(@RequestBody  Company Company,Authentication authentication){
        CompanyService.updateCompany(Company,authentication);
        return "Company Updated!";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{CompanyId}")
    public String deleteCompany(@PathVariable("CompanyId") Long CompanyId, Authentication authentication){
        CompanyService.deleteCompany(CompanyId,authentication);
        return "Company Deleted!";
    }
}
