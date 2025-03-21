package com.authservice.auth_service.service;

import com.authservice.auth_service.dto.AuthRequest;
import com.authservice.auth_service.dto.AuthResponse;
import com.authservice.auth_service.dto.UserDto;
import com.authservice.auth_service.enums.Role;
import com.authservice.auth_service.model.User;
import com.authservice.auth_service.repository.UserRepository;
import com.authservice.auth_service.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private AuthRequest authRequest;
    private User user;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();

        user = User.builder()
                .id("1")
                .username("testUser")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .email("test@email.com")
                .company("TestCompany")
                .createdAt(new Date())
                .build();
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(authRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(anyString(), anyString(), String.valueOf(anyLong()), anyString())).thenReturn("mockedToken");

        AuthResponse response = userService.registerUser(authRequest);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(authRequest));

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginUser_Success() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString(), String.valueOf(anyLong()), anyString())).thenReturn("mockedToken");

        AuthResponse response = userService.loginUser(authRequest);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
    }

    @Test
    void testLoginUser_InvalidUsername() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.loginUser(authRequest));

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testLoginUser_InvalidPassword() {
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.loginUser(authRequest));

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testGetUserInfo_Success() {
        when(jwtUtil.extractUsername("validToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserInfo("validToken");

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getRole().getLabel(), userDto.getRole());
        assertEquals(user.getCompany(), userDto.getCompany());
    }

    @Test
    void testGetUserInfo_UserNotFound() {
        when(jwtUtil.extractUsername("invalidToken")).thenReturn("unknownUser");
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserInfo("invalidToken"));

        assertEquals("User not found", exception.getMessage());
    }
}
