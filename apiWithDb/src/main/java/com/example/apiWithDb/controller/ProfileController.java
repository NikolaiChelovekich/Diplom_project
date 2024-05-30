package com.example.apiWithDb.controller;

import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.mappers.UserMapper;
import com.example.apiWithDb.service.EmployeeService;
import com.example.apiWithDb.service.UserService;
import com.example.apiWithDb.utils.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final UserMapper userMapper;

    public ProfileController(UserService userService, EmployeeService employeeService, UserMapper userMapper) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.userMapper = userMapper;
    }

@GetMapping("/currentUser")
    ResponseEntity<?> getCurrentUserDetails(Authentication authentication){
        User user = userService.findUserByToken(authentication);
        switch (user.getRole()) {
            case USER:
                Employee employee = employeeService.findByLogin(user.getLogin());
                employee.setRole(Role.USER);
                return ResponseEntity.ok(employee);
            case ADMIN:
                UserDto userDto = userMapper.toUserDto(user);
                return ResponseEntity.ok(userDto);
            default:
                throw new IllegalArgumentException("Invalid role specified");
        }

    }

}