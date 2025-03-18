package com.authservice.auth_service.service;

import com.authservice.auth_service.dto.AuthRequest;
import com.authservice.auth_service.dto.AuthResponse;
import com.authservice.auth_service.dto.UserDto;
import com.authservice.auth_service.enums.Role;
import com.authservice.auth_service.model.User;
import com.authservice.auth_service.repository.UserRepository;
import com.authservice.auth_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse registerUser(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null && !request.getRole().isEmpty()
                        ? Role.valueOf(request.getRole().toUpperCase())
                        : Role.USER)
                .email(request.getEmail() != null ? request.getEmail() : "default@example.com")
                .company(request.getCompany() != null ? request.getCompany() : "DB")
                .createdAt(new Date())
                .build();

        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole().getLabel());
        return new AuthResponse(token);
    }

    public AuthResponse loginUser(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().getLabel());
        return new AuthResponse(token);
    }

    public UserDto getUserInfo(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(user.getId(), user.getUsername(), user.getRole().getLabel(), user.getCompany());
    }
}
