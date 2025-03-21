package com.authservice.auth_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleErrorReturns404View() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk()) // Verifica que la respuesta es 200 OK
                .andExpect(view().name("404")); // Verifica que la vista retornada es "404"
    }
}
