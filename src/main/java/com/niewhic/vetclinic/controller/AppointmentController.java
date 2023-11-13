package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.repository.AppointmentRepository;;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;

    @GetMapping
    public Collection<Appointment> getAllAppointments() {
        return appointmentRepository.getAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable long id) {
        return appointmentRepository.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment) {
        appointmentRepository.add(appointment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addAppointments")
    public ResponseEntity<?> addAppointments(@RequestBody List<Appointment> appointments) {
        List <String> errors = new ArrayList<>();
        for (Appointment appointment : appointments) {
            try {
                appointmentRepository.add(appointment);
            } catch (IllegalArgumentException e) {
                errors.add("Error for appointment ID " + appointment.getId() + ": " + e.getMessage());
            }
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable long id) {
        appointmentRepository.delete(id);
    }

    @PutMapping("/{id}")
    public void updateAppointment(@PathVariable long id, @RequestBody Appointment updatedAppointment) {
        appointmentRepository.update(id, updatedAppointment);
    }
}
