package com.authservice.auth_service.model;

import com.authservice.auth_service.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class UserTest {

    @Test
    void testUserConstructorAndGetters() {
        Date now = new Date();
        User user = new User("1", "testUser", "securePassword", Role.USER, "test@example.com", "TechCorp", now);

        assertEquals("1", user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("securePassword", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("TechCorp", user.getCompany());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void testUserBuilder() {
        Date now = new Date();
        User user = User.builder()
                .id("2")
                .username("builderUser")
                .password("password123")
                .role(Role.ADMIN)
                .email("admin@example.com")
                .company("BigCorp")
                .createdAt(now)
                .build();

        assertNotNull(user);
        assertEquals("2", user.getId());
        assertEquals("builderUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("admin@example.com", user.getEmail());
        assertEquals("BigCorp", user.getCompany());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void testSetters() {
        User user = new User();
        Date now = new Date();

        user.setId("3");
        user.setUsername("setterUser");
        user.setPassword("setterPass");
        user.setRole(Role.USER);
        user.setEmail("setter@example.com");
        user.setCompany("SetterCorp");
        user.setCreatedAt(now);

        assertEquals("3", user.getId());
        assertEquals("setterUser", user.getUsername());
        assertEquals("setterPass", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals("setter@example.com", user.getEmail());
        assertEquals("SetterCorp", user.getCompany());
        assertEquals(now, user.getCreatedAt());
    }
}
