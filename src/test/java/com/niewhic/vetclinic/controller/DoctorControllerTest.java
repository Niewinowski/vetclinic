package com.niewhic.vetclinic.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.doctor.command.CreateDoctorCommand;
import com.niewhic.vetclinic.model.doctor.command.EditDoctorCommand;
import com.niewhic.vetclinic.model.doctor.command.EditDoctorPartiallyCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VetclinicApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DoctorControllerTest {

    private final MockMvc postman;

    private final ObjectMapper objectMapper;

    private final DatabaseCleaner databaseCleaner;

    @Autowired
    public DoctorControllerTest(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }

    @AfterEach
    void tearDown() throws Exception {
        databaseCleaner.cleanUp();
    }

    @Test
    void shouldFindAll() throws Exception {
        postman.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Dolittle")))
                .andExpect(jsonPath("$[0].rate", is(50)))
                .andExpect(jsonPath("$[0].specialty", is("Veterinary Medicine")))
                .andExpect(jsonPath("$[0].animalSpecialty", is("All Animals")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Ellie")))
                .andExpect(jsonPath("$[1].lastName", is("Sattler")))
                .andExpect(jsonPath("$[1].rate", is(70)))
                .andExpect(jsonPath("$[1].specialty", is("Paleobotany")))
                .andExpect(jsonPath("$[1].animalSpecialty", is("Dinosaurs")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Newt")))
                .andExpect(jsonPath("$[2].lastName", is("Scamander")))
                .andExpect(jsonPath("$[2].rate", is(45)))
                .andExpect(jsonPath("$[2].specialty", is("Magizoology")))
                .andExpect(jsonPath("$[2].animalSpecialty", is("Magical Creatures")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("Alan")))
                .andExpect(jsonPath("$[3].lastName", is("Grant")))
                .andExpect(jsonPath("$[3].rate", is(65)))
                .andExpect(jsonPath("$[3].specialty", is("Paleontology")))
                .andExpect(jsonPath("$[3].animalSpecialty", is("Dinosaurs")));
    }
    @Test
    void shouldFindById() throws Exception {
        long doctorId = 1;
        postman.perform(get("/doctors/" + doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) doctorId)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Dolittle")))
                .andExpect(jsonPath("$.rate", is(50)))
                .andExpect(jsonPath("$.specialty", is("Veterinary Medicine")))
                .andExpect(jsonPath("$.animalSpecialty", is("All Animals")));
    }
    @Test
    void shouldCreate() throws Exception {
        CreateDoctorCommand command = CreateDoctorCommand.builder()
                .name("Remy")
                .lastName("Ratatouille")
                .rate(30)
                .specialty("Gastronomy")
                .animalSpecialty("Gourmet Rats")
                .build();
        String json = objectMapper.writeValueAsString(command);

        postman.perform(get("/doctors/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Doctor with id 6 not found"))
                .andExpect(jsonPath("$.uri").value("/doctors/6"))
                .andExpect(jsonPath("$.method").value("GET"));
        postman.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.lastName").value(command.getLastName()))
                .andExpect(jsonPath("$.animalSpecialty").value(command.getAnimalSpecialty()))
                .andExpect(jsonPath("$.rate").value(command.getRate()));
        postman.perform(get("/doctors/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Remy"))
                .andExpect(jsonPath("$.lastName").value("Ratatouille"))
                .andExpect(jsonPath("$.animalSpecialty").value("Gourmet Rats"))
                .andExpect(jsonPath("$.rate").value(30));

    }

    @Test
    void shouldDelete() throws Exception {
        long doctorId = 1;

        postman.perform(get("/doctors/" + doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) doctorId)));

        postman.perform(delete("/doctors/" + doctorId))
                .andExpect(status().isNoContent());

        postman.perform(get("/doctors/" + doctorId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldEdit() throws Exception {
        EditDoctorCommand command = EditDoctorCommand.builder()
                .name("Updated Name")
                .lastName("Updated LastName")
                .rate(550)
                .specialty("Updated Specialty")
                .animalSpecialty("Updated Animals")
                .build();
        String json = objectMapper.writeValueAsString(command);
        long doctorId = 1;

        postman.perform(get("/doctors/" + doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) doctorId)))
                .andExpect(jsonPath("$.name", not(is(command.getName())))) // Check that the name is not already the updated value
                .andExpect(jsonPath("$.lastName", not(is(command.getLastName())))); // Check that the last name is not already the updated value

        postman.perform(put("/doctors/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) doctorId)))
                .andExpect(jsonPath("$.name", is(command.getName())))
                .andExpect(jsonPath("$.lastName", is(command.getLastName())))
                .andExpect(jsonPath("$.rate", is(command.getRate())))
                .andExpect(jsonPath("$.specialty", is(command.getSpecialty())))
                .andExpect(jsonPath("$.animalSpecialty", is(command.getAnimalSpecialty())));

        postman.perform(get("/doctors/" + doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) doctorId)))
                .andExpect(jsonPath("$.name", is(command.getName())))
                .andExpect(jsonPath("$.lastName",
                        is(command.getLastName())))
                .andExpect(jsonPath("$.rate", is(command.getRate())))
                .andExpect(jsonPath("$.specialty", is(command.getSpecialty())))
                .andExpect(jsonPath("$.animalSpecialty", is(command.getAnimalSpecialty())));
    }

    @Test
    void shouldEditPartially() throws Exception {
        EditDoctorPartiallyCommand command = EditDoctorPartiallyCommand.builder()
                .lastName(Optional.of("NewLastName"))
                .rate(Optional.of(550))
                .build();
        String commandJson = objectMapper.writeValueAsString(command);

        postman.perform(get("/doctors/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Dolittle"))
                .andExpect(jsonPath("$.rate").value(50))
                .andExpect(jsonPath("$.specialty").value("Veterinary Medicine"))
                .andExpect(jsonPath("$.animalSpecialty").value("All Animals"));

        postman.perform(patch("/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(command.getLastName().get()))
                .andExpect(jsonPath("$.rate").value(command.getRate().get()));

        postman.perform(get("/doctors/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(command.getLastName().get()))
                .andExpect(jsonPath("$.rate").value(command.getRate().get()));
    }

    @Test
    void shouldNotEditPartially() throws Exception {
        long invalidDoctorId = 999;
        EditDoctorPartiallyCommand command = EditDoctorPartiallyCommand.builder()
                .lastName(Optional.of("NewLastName"))
                .rate(Optional.of(550))
                .build();
        String commandJson = objectMapper.writeValueAsString(command);

        postman.perform(patch("/doctors/" + invalidDoctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commandJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}