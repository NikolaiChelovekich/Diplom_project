package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.CredentialsDto;
import com.example.apiWithDb.dto.SignUpDto;
import com.example.apiWithDb.dto.UserDto;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.UserMapper;
import com.example.apiWithDb.repository.UserRepository;
import com.example.apiWithDb.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .login("johndoe")
                .country("USA")
                .role(Role.USER)
                .password("password")
                .build();
    }

    @Test
    void testFindBylogin() {
        // Arrange
        String login = "johndoe";
        when(userRepository.findBylogin(login)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(UserDto.builder()
                .Id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .country(user.getCountry())
                .build());

        // Act
        UserDto result = userService.findBylogin(login);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getCountry(), result.getCountry());
    }

    @Test
    void testLogin_Success() {
        // Arrange
        CredentialsDto credentialsDto = new CredentialsDto("johndoe", "password");
        when(userRepository.findBylogin(credentialsDto.getLogin())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(true);
        when(userMapper.toUserDto(user)).thenReturn(UserDto.builder()
                .Id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .country(user.getCountry())
                .build());

        // Act
        UserDto result = userService.login(credentialsDto);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getCountry(), result.getCountry());
    }

    @Test
    void testLogin_Failure() {
        // Arrange
        CredentialsDto credentialsDto = new CredentialsDto("johndoe", "wrongpassword");
        when(userRepository.findBylogin(credentialsDto.getLogin())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(false);

        // Assert
        assertThrows(AppException.class, () -> userService.login(credentialsDto));
    }


    @Test
    void testRegister_UserAlreadyExists() {
        // Arrange
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName("John")
                .lastName("Doe")
                .login("johndoe")
                .country("USA")
                .password("password")
                .build();
        when(userRepository.findBylogin(signUpDto.getLogin())).thenReturn(Optional.of(user));

        // Assert
        assertThrows(AppException.class, () -> userService.register(signUpDto, Role.USER));
    }

    @Test
    void testFindUserByToken() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        UserDto userDto = UserDto.builder().login("johndoe").build();
        when(authentication.getPrincipal()).thenReturn(userDto);
        when(userRepository.findBylogin(userDto.getLogin())).thenReturn(Optional.of(user));

        // Act
        User result = userService.findUserByToken(authentication);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getCountry(), result.getCountry());
    }
}