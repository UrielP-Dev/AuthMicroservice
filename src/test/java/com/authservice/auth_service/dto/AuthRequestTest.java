package com.authservice.auth_service.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testAuthRequestGettersAndSetters() {
        AuthRequest authRequest = new AuthRequest();

        authRequest.setUsername("testUser");
        authRequest.setPassword("securePass");
        authRequest.setEmail("test@example.com");
        authRequest.setCompany("TestCompany");
        authRequest.setRole("ADMIN");

        assertEquals("testUser", authRequest.getUsername());
        assertEquals("securePass", authRequest.getPassword());
        assertEquals("test@example.com", authRequest.getEmail());
        assertEquals("TestCompany", authRequest.getCompany());
        assertEquals("ADMIN", authRequest.getRole());
    }

    @Test
    void testAuthRequestEqualsAndHashCode() {
        AuthRequest auth1 = new AuthRequest();
        auth1.setUsername("testUser");
        auth1.setPassword("securePass");
        auth1.setEmail("test@example.com");
        auth1.setCompany("TestCompany");
        auth1.setRole("ADMIN");

        AuthRequest auth2 = new AuthRequest();
        auth2.setUsername("testUser");
        auth2.setPassword("securePass");
        auth2.setEmail("test@example.com");
        auth2.setCompany("TestCompany");
        auth2.setRole("ADMIN");

        assertEquals(auth1, auth2);
        assertEquals(auth1.hashCode(), auth2.hashCode());
    }
}
