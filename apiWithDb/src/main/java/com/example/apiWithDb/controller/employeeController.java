package com.example.apiWithDb.controller;


import com.example.apiWithDb.config.UserAuthProvider;
import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.response.ResponseHandler;
import com.example.apiWithDb.service.EmployeeService;
import com.example.apiWithDb.service.UserService;
import com.example.apiWithDb.utils.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/company/{companyId}/departments/{departmentId}/employee")
@SecurityRequirement(name = "bearerAuth")
public class employeeController {

    public EmployeeService employeeService;
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public employeeController(EmployeeService employeeService, UserService userService, UserAuthProvider userAuthProvider)
    {
        this.employeeService = employeeService;
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllEmployeeDetails(@PathVariable("departmentId") Long departmentId)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, employeeService.getAllEmployees(departmentId));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeeDetails(@PathVariable Long employeeId,@PathVariable("departmentId") Long departmentId)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, employeeService.getEmployee(employeeId, departmentId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<UserDto> CreateEmployeeDetails(@RequestBody Employee employee, @PathVariable("departmentId") Long departmentId) {


        UserDto user = userService.register(employeeService.toSignUpDto(employee), Role.USER);
        user.setRole(Role.USER);
        user.setToken(userAuthProvider.createToken(user));

        employeeService.createEmployee(employee, departmentId);

        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public String UpdateEmployeeDetails(@RequestBody Employee employee,@PathVariable("departmentId") Long departmentId)
    {

        employeeService.findById(employee.getId());
        employeeService.updateEmployee(employee,departmentId);
        return "Employee updated!";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId,@PathVariable("departmentId") Long departmentId)
    {
        employeeService.deleteEmployee(employeeId,departmentId);
        return "Employee Deleted!";
    }


}
