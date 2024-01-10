package com.niewhic.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.patient.CreatePatientCommand;
import com.niewhic.vetclinic.model.patient.EditPatientCommand;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @AfterEach
    void tearDown() throws LiquibaseException {
        databaseCleaner.cleanUp();
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
                .andExpect(jsonPath("$.breed").value("Great Dane"))
                .andExpect(jsonPath("$.active").value("true"));
    }

    @Test
    void shouldFindAllPatients() throws Exception {
        postman.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Scooby"))
                .andExpect(jsonPath("$.[0].ownerName").value("Shaggy"))
                .andExpect(jsonPath("$.[0].ownerLastName").value("Rogers"))
                .andExpect(jsonPath("$.[0].dateOfBirth").value("1969-09-13"))
                .andExpect(jsonPath("$.[0].ownerEmail").value("shaggy@mysterymachine.com"))
                .andExpect(jsonPath("$.[0].species").value("Dog"))
                .andExpect(jsonPath("$.[0].breed").value("Great Dane"))
                .andExpect(jsonPath("$.[0].active").value("true"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("Garfield"))
                .andExpect(jsonPath("$.[1].ownerName").value("Jon"))
                .andExpect(jsonPath("$.[1].ownerLastName").value("Arbuckle"))
                .andExpect(jsonPath("$.[1].dateOfBirth").value("1978-06-19"))
                .andExpect(jsonPath("$.[1].ownerEmail").value("jon@arbuckle.com"))
                .andExpect(jsonPath("$.[1].species").value("Cat"))
                .andExpect(jsonPath("$.[1].breed").value("Exotic Shorthair"))
                .andExpect(jsonPath("$.[1].active").value("true"))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[2].name").value("Hedwig"))
                .andExpect(jsonPath("$.[2].ownerName").value("Harry"))
                .andExpect(jsonPath("$.[2].ownerLastName").value("Potter"))
                .andExpect(jsonPath("$.[2].dateOfBirth").value("1991-07-31"))
                .andExpect(jsonPath("$.[2].ownerEmail").value("harry@hogwarts.ac.uk"))
                .andExpect(jsonPath("$.[2].species").value("Bird"))
                .andExpect(jsonPath("$.[2].breed").value("Snowy Owl"))
                .andExpect(jsonPath("$.[2].active").value("true"))
                .andExpect(jsonPath("$.[3].id").value(4))
                .andExpect(jsonPath("$.[3].name").value("Falkor"))
                .andExpect(jsonPath("$.[3].ownerName").value("Atreyu"))
                .andExpect(jsonPath("$.[3].ownerLastName").value("N/A"))
                .andExpect(jsonPath("$.[3].dateOfBirth").value("1984-04-06"))
                .andExpect(jsonPath("$.[3].ownerEmail").value("atreyu@fantasia.com"))
                .andExpect(jsonPath("$.[3].species").value("Dragon"))
                .andExpect(jsonPath("$.[3].breed").value("Luck Dragon"))
                .andExpect(jsonPath("$.[3].active").value("true"))
                .andExpect(jsonPath("$.[4].id").value(5))
                .andExpect(jsonPath("$.[4].name").value("Toothless"))
                .andExpect(jsonPath("$.[4].ownerName").value("Hiccup"))
                .andExpect(jsonPath("$.[4].ownerLastName").value("Haddock"))
                .andExpect(jsonPath("$.[4].dateOfBirth").value("2010-03-26"))
                .andExpect(jsonPath("$.[4].ownerEmail").value("hiccup@berk.com"))
                .andExpect(jsonPath("$.[4].species").value("Dragon"))
                .andExpect(jsonPath("$.[4].breed").value("Night Fury"))
                .andExpect(jsonPath("$.[4].active").value("true"));
    }

    @Test
    void shouldSavePatient() throws Exception {
        CreatePatientCommand command = CreatePatientCommand.builder()
                .name("Puppy")
                .ownerName("Jan")
                .ownerLastName("Kowalski")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .ownerEmail("jan@kowalski.com")
                .species("species")
                .breed("breed")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/patients/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.ownerName").value(command.getOwnerName()))
                .andExpect(jsonPath("$.ownerLastName").value(command.getOwnerLastName()))
                .andExpect(jsonPath("$.dateOfBirth").value(command.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.ownerEmail").value(command.getOwnerEmail()))
                .andExpect(jsonPath("$.species").value(command.getSpecies()))
                .andExpect(jsonPath("$.breed").value(command.getBreed()))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(get("/patients/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Puppy"))
                .andExpect(jsonPath("$.ownerName").value("Jan"))
                .andExpect(jsonPath("$.ownerLastName").value("Kowalski"))
                .andExpect(jsonPath("$.dateOfBirth").value(LocalDate.of(2000, 10, 10).toString()))
                .andExpect(jsonPath("$.ownerEmail").value("jan@kowalski.com"))
                .andExpect(jsonPath("$.species").value("species"))
                .andExpect(jsonPath("$.breed").value("breed"))
                .andExpect(jsonPath("$.active").value("true"));
    }

    @Test
    void shouldDeletePatient() throws Exception {
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
                .andExpect(jsonPath("$.breed").value("Great Dane"))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(delete("/patients/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        postman.perform(get("/patients/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 1 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/1"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void shouldEditPatient() throws Exception {
        EditPatientCommand command = EditPatientCommand.builder()
                .name("Puppy")
                .ownerName("Jan")
                .ownerLastName("Kowalski")
                .ownerEmail("jan@kowalski.com")
                .species("species")
                .breed("breed")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
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
                .andExpect(jsonPath("$.breed").value("Great Dane"))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.ownerName").value(command.getOwnerName()))
                .andExpect(jsonPath("$.ownerLastName").value(command.getOwnerLastName()))
                .andExpect(jsonPath("$.ownerEmail").value(command.getOwnerEmail()))
                .andExpect(jsonPath("$.species").value(command.getSpecies()))
                .andExpect(jsonPath("$.breed").value(command.getBreed()))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(get("/patients/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.ownerName").value(command.getOwnerName()))
                .andExpect(jsonPath("$.ownerLastName").value(command.getOwnerLastName()))
                .andExpect(jsonPath("$.ownerEmail").value(command.getOwnerEmail()))
                .andExpect(jsonPath("$.species").value(command.getSpecies()))
                .andExpect(jsonPath("$.breed").value(command.getBreed()))
                .andExpect(jsonPath("$.active").value("true"));
    }

    @Test
    void shouldNotEditPatient() throws Exception {
        EditPatientCommand command = EditPatientCommand.builder()
                .name("Puppy")
                .ownerName("Jan")
                .ownerLastName("Kowalski")
                .ownerEmail("jan@kowalski.com")
                .species("species")
                .breed("breed")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/patients/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(put("/patients/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/6"))
                .andExpect(jsonPath("$.method").value("PUT"));
    }

    @Test
    void shouldEditPartially() throws Exception {
        EditPatientCommand command = EditPatientCommand.builder()
                .name("Puppy")
                .species("species")
                .breed("breed")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
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
                .andExpect(jsonPath("$.breed").value("Great Dane"))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(patch("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.ownerName").value("Shaggy"))
                .andExpect(jsonPath("$.ownerLastName").value("Rogers"))
                .andExpect(jsonPath("$.ownerEmail").value("shaggy@mysterymachine.com"))
                .andExpect(jsonPath("$.species").value(command.getSpecies()))
                .andExpect(jsonPath("$.breed").value(command.getBreed()))
                .andExpect(jsonPath("$.active").value("true"));
        postman.perform(get("/patients/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.ownerName").value("Shaggy"))
                .andExpect(jsonPath("$.ownerLastName").value("Rogers"))
                .andExpect(jsonPath("$.ownerEmail").value("shaggy@mysterymachine.com"))
                .andExpect(jsonPath("$.species").value(command.getSpecies()))
                .andExpect(jsonPath("$.breed").value(command.getBreed()))
                .andExpect(jsonPath("$.active").value("true"));
    }

    @Test
    void shouldNotEditPartially() throws Exception {
        EditPatientCommand command = EditPatientCommand.builder()
                .name("Puppy")
                .species("species")
                .breed("breed")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/patients/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(patch("/patients/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/patients/6"))
                .andExpect(jsonPath("$.method").value("PATCH"));
    }
}
