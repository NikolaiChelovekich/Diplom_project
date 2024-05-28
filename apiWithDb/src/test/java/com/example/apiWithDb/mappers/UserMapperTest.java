package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.utils.Role;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testSignUpToUser() {
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName("John")
                .lastName("Doe")
                .login("johndoe")
                .country("USA")
                .password("password123")
                .build();

        User user = userMapper.signUpToUser(signUpDto);

        assertNotNull(user);
        assertEquals(signUpDto.getFirstName(), user.getFirstName());
        assertEquals(signUpDto.getLastName(), user.getLastName());
        assertEquals(signUpDto.getLogin(), user.getLogin());
        assertEquals(signUpDto.getCountry(), user.getCountry());
        assertEquals(signUpDto.getPassword(), user.getPassword());
    }

    @Test
    void testSignUpToUser_PasswordIgnored() {
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .login("janesmith")
                .country("Canada")
                .password("password456")
                .build();

        User user = userMapper.signUp(signUpDto);

        assertNotNull(user);
        assertEquals(signUpDto.getFirstName(), user.getFirstName());
        assertEquals(signUpDto.getLastName(), user.getLastName());
        assertEquals(signUpDto.getLogin(), user.getLogin());
        assertEquals(signUpDto.getCountry(), user.getCountry());
        assertNull(user.getPassword()); // Ensure password is ignored due to @Mapping(target = "password", ignore = true)
    }
}