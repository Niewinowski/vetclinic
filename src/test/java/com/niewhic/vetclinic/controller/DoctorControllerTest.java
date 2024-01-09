package com.niewhic.vetclinic.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niewhic.vetclinic.DatabaseCleaner;
import com.niewhic.vetclinic.VetclinicApplication;
import com.niewhic.vetclinic.model.doctor.CreateDoctorCommand;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import com.niewhic.vetclinic.model.doctor.EditDoctorCommand;
import com.niewhic.vetclinic.model.doctor.EditDoctorPartiallyCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    // TODO test findById
    @Test
    void shouldFindAll() throws Exception {
        postman.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Dolittle")))
                .andExpect(jsonPath("$[0].rate", is(500)))
                .andExpect(jsonPath("$[0].specialty", is("Veterinary Medicine")))
                .andExpect(jsonPath("$[0].animalSpecialty", is("All Animals")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Ellie")))
                .andExpect(jsonPath("$[1].lastName", is("Sattler")))
                .andExpect(jsonPath("$[1].rate", is(700)))
                .andExpect(jsonPath("$[1].specialty", is("Paleobotany")))
                .andExpect(jsonPath("$[1].animalSpecialty", is("Dinosaurs")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Newt")))
                .andExpect(jsonPath("$[2].lastName", is("Scamander")))
                .andExpect(jsonPath("$[2].rate", is(450)))
                .andExpect(jsonPath("$[2].specialty", is("Magizoology")))
                .andExpect(jsonPath("$[2].animalSpecialty", is("Magical Creatures")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("Alan")))
                .andExpect(jsonPath("$[3].lastName", is("Grant")))
                .andExpect(jsonPath("$[3].rate", is(650)))
                .andExpect(jsonPath("$[3].specialty", is("Paleontology")))
                .andExpect(jsonPath("$[3].animalSpecialty", is("Dinosaurs")));
    }

    @Test
    void shouldCreate() throws Exception {
        CreateDoctorCommand command = CreateDoctorCommand.builder()
                .name("Remy")
                .lastName("Ratatouille")
                .rate(300)
                .specialty("Gastronomy")
                .animalSpecialty("Gourmet Rats")
                .build();
        String json = objectMapper.writeValueAsString(command);

        // TODO puscic GET czy faktycznie doctor o tym ID  NIE jest w bazei


        postman.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(command.getName())))
                .andExpect(jsonPath("$.lastName", is(command.getLastName())));

        // TODO puscic GET czy faktycznie doctor o tym ID jest w bazei
    }

    @Test
    void shouldDelete() throws Exception {
        long doctorId = 1;

        // TODO get czy to co usuwasz faktycznie najpierw istnieje

        postman.perform(delete("/doctors/" + doctorId))
                .andExpect(status().isNoContent());
        // TODO rozbudowac sprawdzenie bledu
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

        // TODO puscic GET czy faktycznie doctor o tym ID  NIE jest w bazei


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

        // TODO puscic GET czy faktycznie doctor o tym ID jest w bazei

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
                .andExpect(jsonPath("$.rate").value(500))
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