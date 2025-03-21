package com.authservice.auth_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        assertNotNull(openAPI, "El OpenAPI no debe ser nulo");
        assertEquals("Auth API", openAPI.getInfo().getTitle(), "El título de la API no es el esperado");
        assertEquals("1.0", openAPI.getInfo().getVersion(), "La versión de la API no es la esperada");
        assertEquals("Auth Microservice API Documentation", openAPI.getInfo().getDescription(), "La descripción no es la esperada");

        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
        assertTrue(securityRequirement.containsKey("BearerAuth"), "Debe contener BearerAuth en seguridad");

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("BearerAuth");
        assertNotNull(securityScheme, "El esquema de seguridad no debe ser nulo");
        assertEquals("bearer", securityScheme.getScheme(), "El esquema de seguridad debe ser 'bearer'");
        assertEquals("JWT", securityScheme.getBearerFormat(), "El formato de seguridad debe ser 'JWT'");
    }
}
