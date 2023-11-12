package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import com.niewhic.vetclinic.repository.DoctorRepository;
import com.niewhic.vetclinic.repository.PatientRepository;
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
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

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
        Doctor doctor = doctorRepository.getById(appointment.getDoctorId());
        Patient patient = patientRepository.getById(appointment.getPatientId());

        if (doctor == null || patient == null) {
            return ResponseEntity.badRequest().body("Doctor or Patient not found with the given IDs");
        }

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

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
