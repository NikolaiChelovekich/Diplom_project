package com.example.apiWithDb.controller;

import com.example.apiWithDb.response.ResponseHandler;
import com.example.apiWithDb.service.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    public CompanyService CompanyService;

    public CompanyController(CompanyService CompanyService) {
        this.CompanyService = CompanyService;
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
    public String CreateCompanyDetails(@RequestBody com.example.apiWithDb.entities.Company Company)
    {
        CompanyService.createCompany(Company);
        return "Company Created!";
    }

    @PutMapping
    public String UpdateCompanyDetails(@RequestBody com.example.apiWithDb.entities.Company Company)
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
