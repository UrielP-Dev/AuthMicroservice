package com.authservice.auth_service.controller;

import com.authservice.auth_service.dto.AuthRequest;
import com.authservice.auth_service.dto.AuthResponse;
import com.authservice.auth_service.dto.UserDto;
import com.authservice.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/me")
    public UserDto getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return userService.getUserInfo(token);
    }
}
