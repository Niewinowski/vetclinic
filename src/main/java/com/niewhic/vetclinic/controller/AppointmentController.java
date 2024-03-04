package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.AppointmentDto;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.appointment.command.EditAppointmentCommand;
import com.niewhic.vetclinic.model.office.OfficeDto;
import com.niewhic.vetclinic.model.patient.PatientDto;
import com.niewhic.vetclinic.model.token.Token;
import com.niewhic.vetclinic.service.AppointmentService;;
import com.niewhic.vetclinic.service.EmailService;
import com.niewhic.vetclinic.service.TokenService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Gets all appointments", description = "Returns a list of appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the appointments",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AppointmentDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No appointments found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Gets Appointment by ID", description = "For valid response try integer IDs with value >= 1 and <= 5. Other values may generate exceptions")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of appointment that needs to be fetched")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patient",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable long id) {
        return ResponseEntity.ok(modelMapper.map(appointmentService.findById(id), AppointmentDto.class));
    }

    @Operation(summary = "Post an appointment")
    @Parameters(value = {
            @Parameter(name = "body", description = "CreateAppointmentCommand object that is converted to Appointment object")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Appointment details supplied", content = @Content)})
    @PostMapping
    public ResponseEntity<AppointmentDto> addAppointment(@Valid @RequestBody CreateAppointmentCommand command) {
        // TODO wyniesc logike biznesowa do service
        Appointment savedAppointment = appointmentService.save(command);
        Token token = tokenService.generate(savedAppointment);
        String languagePreference = "en";
        String confirmationLink = tokenService.generateConfirmationUrl(token);
        emailService.sendConfirmationEmail(savedAppointment.getPatient().getOwnerEmail(), languagePreference, savedAppointment.getPatient().getName(), savedAppointment.getId(), confirmationLink);
        return new ResponseEntity<>(modelMapper.map(savedAppointment, AppointmentDto.class), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an appointment", description = "Deletes an appointment object by its id")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of appointment that has to be deleted")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updates an Appointment")
    @Parameters(value = {
            @Parameter(name = "body", description = "EditAppointmentCommand object that is converted to Appointment object"),
            @Parameter(name = "id", description = "ID of a appointment to update")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the appointment",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable long id, @RequestBody EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = appointmentService.edit(id, updatedCommand);
        return ResponseEntity.ok(modelMapper.map(updatedAppointment, AppointmentDto.class));
    }
    @Operation(summary = "Edits an appointment")
    @Parameters(value = {
            @Parameter(name = "body", description = "EditAppointmentCommand object that is converted to Appointment object"),
            @Parameter(name = "id", description = "ID of a appointment to edit")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited the appointment",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)})
    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDto> editAppointment(@PathVariable long id, @RequestBody EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = appointmentService.editPartially(id, updatedCommand);
        return ResponseEntity.ok(modelMapper.map(updatedAppointment, AppointmentDto.class));
    }


}
