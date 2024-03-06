package com.niewhic.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.appointment.command.EditAppointmentCommand;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
        postman.perform(get("/appointments/1")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctor.id").value(1))
                .andExpect(jsonPath("$.patient.id").value(1))
                .andExpect(jsonPath("$.dateTime").value("2024-09-01T10:00:00"))
                .andExpect(jsonPath("$.notes").value("Regular check-up for Scooby after a mystery adventure."))
                .andExpect(jsonPath("$.prescription").value("Vitamin snacks for bravery."));


    }

    @Test
    void shouldSaveAppointment() throws Exception {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .doctorId(1L)
                .patientId(1L)
                .dateTime(LocalDateTime.of(2025, 10, 10, 0, 0, 0))
                .notes("notes")
                .prescription("prescription")
                .officeId(1L)
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/appointments/6")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(post("/appointments")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6L))
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.patient.id").value(command.getPatientId()))
                .andExpect(jsonPath("$.dateTime").value("2025-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()))
                .andExpect(jsonPath("$.office.id").value(command.getOfficeId()));

        postman.perform(get("/appointments/6")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(6L))
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.patient.id").value(command.getPatientId()))
                .andExpect(jsonPath("$.dateTime").value("2025-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
    }
    @Test
    void shouldDelete() throws Exception {
        // Given
        long appointmentId = 1;

        // When
        postman.perform(delete("/appointments/" + appointmentId)
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Then
        postman.perform(get("/appointments/" + appointmentId)
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id " + appointmentId + " not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/" + appointmentId))
                .andExpect(jsonPath("$.method").value("GET"));
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
        postman.perform(get("/appointments/1")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctor.id").value(1))
                .andExpect(jsonPath("$.patient.id").value(1))
                .andExpect(jsonPath("$.dateTime").value("2024-09-01T10:00:00"))
                .andExpect(jsonPath("$.notes").value("Regular check-up for Scooby after a mystery adventure."))
                .andExpect(jsonPath("$.prescription").value("Vitamin snacks for bravery."));
        postman.perform(put("/appointments/1")
                        .with(httpBasic("admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(commandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.dateTime").value("2022-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
        postman.perform(get("/appointments/1")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(jsonPath("$.doctor.id").value(command.getDoctorId()))
                .andExpect(jsonPath("$.dateTime").value("2022-10-10T00:00:00"))
                .andExpect(jsonPath("$.notes").value(command.getNotes()))
                .andExpect(jsonPath("$.prescription").value(command.getPrescription()));
    }

    @Test
    void shouldNotEditAppointment() throws Exception {
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.of(2022, 10, 10, 0, 0, 0))
                .notes("new notes")
                .prescription("new prescription")
                .build();
        String commandJson = objectMapper.writeValueAsString(command);
        postman.perform(get("/appointments/6")
                        .with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Appointment with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/appointments/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(put("/appointments/6")
                        .with(httpBasic("admin", "admin"))
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
    @Test
    void shouldNotSaveAppointmentPatientBusy() throws Exception {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .doctorId(4L)
                .patientId(5L)
                .dateTime(LocalDateTime.of(2024, 9, 5, 15, 0, 0))
                .notes("notes")
                .prescription("prescription")
                .officeId(1L)
                .build();
        String commandJson = objectMapper.writeValueAsString(command);

        postman.perform(post("/appointments")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Patient has other appointment in this time."))
                .andExpect(jsonPath("$.uri").value("/appointments"))
                .andExpect(jsonPath("$.method").value("POST"));
    }
    @Test
    void shouldNotSaveAppointmentDoctorBusy() throws Exception {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .doctorId(5L)
                .patientId(4L)
                .dateTime(LocalDateTime.of(2024, 9, 5, 15, 0, 0))
                .notes("notes")
                .prescription("prescription")
                .officeId(1L)
                .build();
        String commandJson = objectMapper.writeValueAsString(command);

        postman.perform(post("/appointments")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Doctor has other appointment in this time."))
                .andExpect(jsonPath("$.uri").value("/appointments"))
                .andExpect(jsonPath("$.method").value("POST"));
    }
}

//    @MockBean
//    private JavaMailSender javaMailSender;
//
//    @Test
//    void shouldSaveAppointment() throws Exception {
//        // Assuming you have a service that sends an email
//        // Configure Mockito to do nothing when sending an email
//        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
//
//        // Your test code here
//    }
