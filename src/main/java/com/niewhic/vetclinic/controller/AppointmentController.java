package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.service.AppointmentService;;
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
    private final AppointmentService appointmentService;

//    @GetMapping
//    public Collection<Appointment> getAllAppointments() {
//        return appointmentService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public Appointment getAppointmentById(@PathVariable long id) {
//        return appointmentService.getById(id);
//    }

    @PostMapping
    public ResponseEntity<?> addAppointment(@RequestBody CreateAppointmentCommand command) {
        appointmentService.save(command);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/addAppointments")
//    public ResponseEntity<?> addAppointments(@RequestBody List<Appointment> appointments) {
//        List <String> errors = new ArrayList<>();
//        for (Appointment appointment : appointments) {
//            try {
//                appointmentService.add(appointment);
//            } catch (IllegalArgumentException e) {
//                errors.add("Error for appointment ID " + appointment.getId() + ": " + e.getMessage());
//            }
//        }
//        if (!errors.isEmpty()) {
//            return ResponseEntity.badRequest().body(errors);
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteAppointment(@PathVariable long id) {
//        appointmentService.delete(id);
//    }
//
//    @PutMapping("/{id}")
//    public void updateAppointment(@PathVariable long id, @RequestBody Appointment updatedAppointment) {
//        appointmentService.update(id, updatedAppointment);
//    }
}
