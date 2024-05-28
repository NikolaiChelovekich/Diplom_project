package com.example.apiWithDb.controller;

import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.service.EmployeeService;
import com.example.apiWithDb.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    public UserController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    private final UserService userService;
    private final EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<?> getCurrentUserDetails(Authentication authentication) {

    }
}
