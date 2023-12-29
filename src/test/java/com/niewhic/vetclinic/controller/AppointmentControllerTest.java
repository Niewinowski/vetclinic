package com.niewhic.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.appointment.EditAppointmentCommand;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = VetclinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppointmentControllerTest {

    private final MockMvc postman;
    private final ObjectMapper objectMapper;
    private final DatabaseCleaner databaseCleaner;

    @Autowired
    public AppointmentControllerTest(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }

    @AfterEach
    void tearDown() throws LiquibaseException {
        databaseCleaner.cleanUp();
    }

    @Test
    void shouldFindAppointmentById() throws Exception {
        // Given
        // When
        // Then
        postman.perform(get("/appointments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctor.id").value(1))
                .andExpect(jsonPath("$.patient.id").value(1))
                // TODO verify why hour doesn't match dataset
                .andExpect(jsonPath("$.dateTime").value("2023-09-01T00:00:00"))
                .andExpect(jsonPath("$.notes").value("Regular check-up for Scooby after a mystery adventure."))
                .andExpect(jsonPath("$.prescription").value("Vitamin snacks for bravery."));

    }

    @Test
    void shouldSaveAppointment() throws Exception {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .doctorId(1L)
                .patientId(1L)
                .dateTime(LocalDateTime.of(2020, 10, 10, 0, 0, 0))
                .notes("notes")
                .prescription("prescription")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/appointments/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.patient.id").value(command.getPatientId()))
                .andExpect(jsonPath("$.dateTime").value("2020-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
        postman.perform(get("/appointments/6"))
                .andDo(print())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.patient.id").value(command.getPatientId()))
                .andExpect(jsonPath("$.dateTime").value("2020-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
    }

    @Test
    void shouldEditAppointment() throws Exception {
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.of(2022, 10, 10, 0, 0, 0))
                .notes("new notes")
                .prescription("new prescription")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/appointments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctor.id").value(1))
                .andExpect(jsonPath("$.patient.id").value(1))
                .andExpect(jsonPath("$.dateTime").value("2023-09-01T00:00:00"))
                .andExpect(jsonPath("$.notes").value("Regular check-up for Scooby after a mystery adventure."))
                .andExpect(jsonPath("$.prescription").value("Vitamin snacks for bravery."));
        postman.perform(put("/appointments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.dateTime").value("2022-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
        postman.perform(get("/appointments/1"))
                .andDo(print())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.dateTime").value("2022-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
    }

    @Test
    void shouldNotEditPatient() throws Exception {
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.of(2022, 10, 10, 0, 0, 0))
                .notes("new notes")
                .prescription("new prescription")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/appointments/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(put("/appointments/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/6"))
                .andExpect(jsonPath("$.method").value("PUT"));
    }
}