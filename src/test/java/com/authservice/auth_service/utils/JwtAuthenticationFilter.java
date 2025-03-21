package com.authservice.auth_service.utils;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext(); // Limpia el contexto antes de cada test
    }

    @Test
    void testValidToken() throws ServletException, IOException {
        // Simula un token válido
        String token = "valid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica que el request pasó el filtro y se llamó al siguiente filtro en la cadena
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testInvalidToken() throws ServletException, IOException {
        // Simula un token inválido
        String token = "invalid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenThrow(new JwtException("Invalid Token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica que el response retornó un 401 (UNAUTHORIZED)
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(response).getWriter();
        verify(filterChain, never()).doFilter(request, response); // No pasa al siguiente filtro
    }

    @Test
    void testNoTokenProvided() throws ServletException, IOException {
        // Simula una petición sin token
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Debe continuar con la cadena de filtros sin autenticación
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(anyInt()); // No debe modificar el response
    }
}
