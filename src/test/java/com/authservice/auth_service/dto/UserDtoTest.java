package com.authservice.auth_service.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void testNoArgsConstructor() {
        UserDto userDto = new UserDto();
        assertNotNull(userDto);
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        String expectedId = "123";
        String expectedUsername = "testUser";
        String expectedRole = "ADMIN";
        String expectedCompany = "TestCompany";

        UserDto userDto = new UserDto(expectedId, expectedUsername, expectedRole, expectedCompany);

        assertEquals(expectedId, userDto.getId());
        assertEquals(expectedUsername, userDto.getUsername());
        assertEquals(expectedRole, userDto.getRole());
        assertEquals(expectedCompany, userDto.getCompany());
    }

    @Test
    void testSetters() {
        UserDto userDto = new UserDto();

        userDto.setId("456");
        userDto.setUsername("newUser");
        userDto.setRole("USER");
        userDto.setCompany("NewCompany");

        assertEquals("456", userDto.getId());
        assertEquals("newUser", userDto.getUsername());
        assertEquals("USER", userDto.getRole());
        assertEquals("NewCompany", userDto.getCompany());
    }

    @Test
    void testEqualsAndHashCode() {
        UserDto user1 = new UserDto("789", "testUser", "ADMIN", "CompanyA");
        UserDto user2 = new UserDto("789", "testUser", "ADMIN", "CompanyA");
        UserDto user3 = new UserDto("999", "differentUser", "USER", "CompanyB");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
