package com.authservice.auth_service.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String label;

    Role(String label) {
        this.label = label;
    }
}
