package com.example.apiWithDb.controller;

import com.example.apiWithDb.config.UserAuthProvider;
import com.example.apiWithDb.dto.CredentialsDto;
import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.service.UserService;
import com.example.apiWithDb.utils.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentialsDto){
       UserDto user  = userService.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody SignUpDto signUpDto)
    {
        UserDto user = userService.register(signUpDto, Role.ADMIN);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }

}
