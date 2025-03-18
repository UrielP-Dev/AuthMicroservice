package com.authservice.auth_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String email;
    private String company;
    private String role;
}
