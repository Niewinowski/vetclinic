package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.doctor.command.CreateDoctorCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import com.niewhic.vetclinic.model.doctor.command.CreateDoctorPageCommand;
import com.niewhic.vetclinic.model.doctor.command.EditDoctorCommand;
import com.niewhic.vetclinic.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;
    @Operation(summary = "Gets all Doctors", description = "Retrieves a list of all doctors available in the system with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of doctors",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DoctorDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No doctors found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors(CreateDoctorPageCommand command) {
        Pageable pageable = modelMapper.map(command, Pageable.class);
        List<DoctorDto> doctors = doctorService.findAll(pageable).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctors);
    }
    @Operation(summary = "Gets Doctor by ID", description = "Fetches a doctor from the system by their ID")
    @Parameter(name = "id", description = "ID of the doctor to be fetched", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the doctor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(modelMapper.map(doctor, DoctorDto.class));
    }
    @Operation(summary = "Add a new Doctor", description = "Creates a new doctor entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@Validated @RequestBody CreateDoctorCommand command) {
        Doctor doctor = modelMapper.map(command, Doctor.class);
        Doctor savedDoctor = doctorService.save(doctor);
        DoctorDto doctorDto = modelMapper.map(savedDoctor, DoctorDto.class);
        return new ResponseEntity<>(doctorDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an existing Doctor", description = "Deletes a doctor entry from the system by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid Doctor ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Update an existing Doctor", description = "Updates a doctor entry in the system by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable long id, @RequestBody EditDoctorCommand command) {
        Doctor doctorToUpdate = modelMapper.map(command, Doctor.class);
        doctorToUpdate.setId(id);
        Doctor updatedDoctor = doctorService.edit(id, doctorToUpdate);
        return ResponseEntity.ok(modelMapper.map(updatedDoctor, DoctorDto.class));
    }
    @Operation(summary = "Partially update an existing Doctor", description = "Partially updates a doctor entry in the system by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor partially updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> editDoctor(@PathVariable long id, @RequestBody EditDoctorCommand command) {
        Doctor partialUpdateDoctor = modelMapper.map(command, Doctor.class);
        Doctor updatedDoctor = doctorService.editPartially(id, partialUpdateDoctor);
        DoctorDto doctorDto = modelMapper.map(updatedDoctor, DoctorDto.class);
        return ResponseEntity.ok(doctorDto);
    }

}
