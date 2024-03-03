package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.*;
import com.niewhic.vetclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")

@ApiResponse(description = "VetClinic's Patients ")

public class PatientController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients(@Valid CreatePatientPageCommand command) {
        Pageable pageable = modelMapper.map(command, Pageable.class);
        List<PatientDto> patientDtoList = patientService.findAll(pageable)
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
        return ResponseEntity.ok(patientDtoList);
    }

    @Operation(summary = "Gets Patient by ID",
            description= "Patient must exist")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of patient that needs to be fetched")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patient",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable long id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity.ok(modelMapper.map(patient, PatientDto.class));
    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody CreatePatientCommand command) {
        Patient savedPatient = patientService.save(modelMapper.map(command, Patient.class));
        return new ResponseEntity<>(modelMapper.map(savedPatient, PatientDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable long id, @RequestBody EditPatientCommand command) {
        Patient updatedPatient = patientService.edit(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.edit(id, updatedPatient), PatientDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDto> editPatient(@PathVariable long id, @RequestBody EditPatientCommand command) {
        Patient editedPatient = patientService.editPartially(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.editPartially(id, editedPatient), PatientDto.class));
    }
}
