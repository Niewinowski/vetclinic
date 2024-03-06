package com.niewhic.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.office.command.CreateOfficeCommand;
import com.niewhic.vetclinic.model.office.command.EditOfficeCommand;
import com.niewhic.vetclinic.model.office.command.EditOfficePartiallyCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VetclinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OfficeControllerTest {
    private final MockMvc postman;

    private final ObjectMapper objectMapper;

    private final DatabaseCleaner databaseCleaner;
    @Autowired
    public OfficeControllerTest(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }
    @AfterEach
    void tearDown() throws Exception {
        databaseCleaner.cleanUp();
    }

    @Test
    void shouldFindAllOffices() throws Exception {
        postman.perform(get("/offices")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))));
    }

    @Test
    void shouldFindOfficeById() throws Exception {
        long officeId = 1;
        postman.perform(get("/offices/" + officeId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) officeId)))
                .andExpect(jsonPath("$.type", isOneOf("CONSULTING", "SURGERY")));
    }

    @Test
    void shouldCreate() throws Exception {
        CreateOfficeCommand createOfficeCommand = CreateOfficeCommand.builder()
                .type("SURGERY")
                .build();

        String json = objectMapper.writeValueAsString(createOfficeCommand);

        String responseContent = postman.perform(post("/offices")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.type", is(createOfficeCommand.getType())))
                .andReturn().getResponse().getContentAsString();

        Long newOfficeId = JsonPath.parse(responseContent).read("$.id", Long.class);

        postman.perform(get("/offices/" + newOfficeId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(newOfficeId.intValue())))
                .andExpect(jsonPath("$.type", is(createOfficeCommand.getType())));
    }

    @Test
    void shouldDeleteOffice() throws Exception {
        long officeId = 1;

        postman.perform(delete("/offices/" + officeId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNoContent());

        postman.perform(get("/offices/" + officeId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldEditOffice() throws Exception {
        EditOfficeCommand editOfficeCommand = EditOfficeCommand.builder()
                .type("CONSULTING")
                .build();

        long officeId = 1;
        String json = objectMapper.writeValueAsString(editOfficeCommand);

        postman.perform(put("/offices/" + officeId)
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) officeId)))
                .andExpect(jsonPath("$.type", is(editOfficeCommand.getType())));
    }

    @Test
    void shouldEditOfficePartially() throws Exception {
        long officeId = 1;
        EditOfficePartiallyCommand partialCommand = EditOfficePartiallyCommand.builder()
                .type("CONSULTING")
                .build();
        String json = objectMapper.writeValueAsString(partialCommand);

        postman.perform(patch("/offices/" + officeId)
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) officeId)))
                .andExpect(jsonPath("$.type", is(partialCommand.getType())));
    }

    @Test
    void shouldHandleNotFoundForNonExistingOffice() throws Exception {
        long invalidOfficeId = 9999;
        postman.perform(get("/offices/" + invalidOfficeId)
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isNotFound());
    }
}
