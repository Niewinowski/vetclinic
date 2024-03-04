package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.*;
import com.niewhic.vetclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
@ApiResponse(description = "VetClinic's Patients ")
public class PatientController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Gets all patients", description = "Returns a page of patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patients",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatientDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No patients found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients(@Valid CreatePatientPageCommand command) {
        Pageable pageable = modelMapper.map(command, Pageable.class);
        List<PatientDto> patientDtoList = patientService.findAll(pageable)
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
        return ResponseEntity.ok(patientDtoList);
    }

    @Operation(summary = "Gets Patient by ID", description = "For valid response try integer IDs with value >= 1 and <= 5. Other values may generate exceptions")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of patient that needs to be fetched")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patient",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable long id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity.ok(modelMapper.map(patient, PatientDto.class));
    }

    @Operation(summary = "Post a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Patient details supplied", content = @Content)})
    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody(description = "CreatePatientCommand object that is converted to Patient object") CreatePatientCommand command) {
        Patient savedPatient = patientService.save(modelMapper.map(command, Patient.class));
        return new ResponseEntity<>(modelMapper.map(savedPatient, PatientDto.class), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a patient", description = "Deletes a patient object by its id")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of patient that has to be deleted")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updates a patient")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of a patient to update")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the patient",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable long id,
                                                    @RequestBody(description = "EditPatientCommand object that is converted to Patient object") EditPatientCommand command) {
        Patient updatedPatient = patientService.edit(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.edit(id, updatedPatient), PatientDto.class));
    }

    @Operation(summary = "Edits a patient")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of a patient to edit")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited the patient",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)})
    @PatchMapping("/{id}")
    public ResponseEntity<PatientDto> editPatient(@PathVariable long id,
                                                  @RequestBody(required = true, description = "EditPatientCommand object that is converted to Patient object") EditPatientCommand command) {
        Patient editedPatient = patientService.editPartially(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.editPartially(id, editedPatient), PatientDto.class));
    }
}
