package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.AppointmentDto;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.appointment.command.EditAppointmentCommand;
import com.niewhic.vetclinic.service.AppointmentService;;
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
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable long id) {
        return ResponseEntity.ok(modelMapper.map(appointmentService.findById(id), AppointmentDto.class));
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> addAppointment(@RequestBody CreateAppointmentCommand command) {
        Appointment savedAppointment = appointmentService.save(command);
        return new ResponseEntity<>(modelMapper.map(savedAppointment, AppointmentDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable long id, @RequestBody EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = appointmentService.edit(id, updatedCommand);
        return ResponseEntity.ok(modelMapper.map(updatedAppointment, AppointmentDto.class));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDto> editAppointment(@PathVariable long id, @RequestBody EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = appointmentService.editPartially(id, updatedCommand);
        return ResponseEntity.ok(modelMapper.map(updatedAppointment, AppointmentDto.class));
    }


}
