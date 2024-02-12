package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VetclinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConfirmEmailControllerTest {
    private final MockMvc postman;
    @Mock
    private final TokenService tokenService;

    @Autowired
    public ConfirmEmailControllerTest(MockMvc postman, TokenService tokenService) {
        this.postman = postman;
        this.tokenService = tokenService;
    }

    @Test
    void confirmEmail_returnsOk() {
        String validToken = "token";
        when(tokenService.confirmEmail(validToken)).thenReturn(true);

        boolean result = tokenService.confirmEmail(validToken);

        assertTrue(result);
    }

    @Test
    void confirmEmail_returnsBadRequest() throws Exception {
        String invalidToken = "invalidToken";
        when(tokenService.confirmEmail(invalidToken)).thenReturn(false);

        postman.perform(get("/confirm-email?token=" + invalidToken))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}