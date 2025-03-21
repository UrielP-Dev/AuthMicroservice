package com.authservice.auth_service.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void testAuthResponseConstructorAndGetter() {
        String expectedToken = "sample.jwt.token";
        AuthResponse authResponse = new AuthResponse(expectedToken);

        assertEquals(expectedToken, authResponse.getToken());
    }

    @Test
    void testAuthResponseEqualsAndHashCode() {
        AuthResponse auth1 = new AuthResponse("sample.jwt.token");
        AuthResponse auth2 = new AuthResponse("sample.jwt.token");
        AuthResponse auth3 = new AuthResponse("different.token");

        assertEquals(auth1, auth2);
        assertEquals(auth1.hashCode(), auth2.hashCode());

        assertNotEquals(auth1, auth3);
        assertNotEquals(auth1.hashCode(), auth3.hashCode());
    }
}
