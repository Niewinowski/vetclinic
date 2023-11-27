package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.service.AppointmentService;;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    // TODO zwracac AppointmentDto
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    // TODO zwracac AppointmentDto
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    // TODO zwracac AppointmentDto
    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody CreateAppointmentCommand command) {
        Appointment savedAppointment = appointmentService.save(command);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // TODO zwracac AppointmentDto
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable long id, @RequestBody CreateAppointmentCommand updatedCommand) {
        return ResponseEntity.ok(appointmentService.edit(id, updatedCommand));
    }
    // TODO zwracac AppointmentDto
    @PatchMapping("/{id}")
    public ResponseEntity<Appointment> editAppointment(@PathVariable long id, @RequestBody CreateAppointmentCommand updatedCommand) {
        return ResponseEntity.ok(appointmentService.editPartially(id, updatedCommand));

    }
}
