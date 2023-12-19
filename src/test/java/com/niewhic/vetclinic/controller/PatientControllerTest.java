package com.niewhic.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VetclinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatientControllerTest {
    private final MockMvc postman;
    private final ObjectMapper objectMapper;
    private final DatabaseCleaner databaseCleaner;

    @Autowired
    public PatientControllerTest(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }

    @Test
    void shouldFindPatientById() throws Exception {
        // Given
        // When
        // Then
        postman.perform(get("/patients/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Scooby"))
                .andExpect(jsonPath("$.ownerName").value("Shaggy"))
                .andExpect(jsonPath("$.ownerLastName").value("Rogers"))
                .andExpect(jsonPath("$.dateOfBirth").value("1969-09-13"))
                .andExpect(jsonPath("$.ownerEmail").value("shaggy@mysterymachine.com"))
                .andExpect(jsonPath("$.species").value("Dog"))
                .andExpect(jsonPath("$.breed").value("Great Dane"));

    }
}
