package com.authservice.auth_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("MySuperSecretKeyMySuperSecretKey".getBytes());
    private static final long EXPIRATION_TIME = 3600000; // 1 hora en milisegundos

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET_KEY, EXPIRATION_TIME);
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        String username = "testUser";
        String role = "USER";
        String id = "123";
        String company = "TestCompany";

        String token = jwtUtil.generateToken(username, role, id, company);
        assertNotNull(token, "El token no debería ser nulo");

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername, "El nombre de usuario extraído no coincide");
    }

    @Test
    void testTokenExpiration() {
        String token = jwtUtil.generateToken("user", "ROLE", "1", "Company");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();
        assertNotNull(expirationDate, "La fecha de expiración no debería ser nula");
        assertTrue(expirationDate.after(new Date()), "La fecha de expiración debe estar en el futuro");
    }
}
