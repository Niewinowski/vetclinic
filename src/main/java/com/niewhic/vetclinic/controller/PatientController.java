package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addPatient(@RequestBody Patient patient) {
        patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.edit(id, patient));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Patient> editPatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
        return ResponseEntity.ok(patientService.editPartially(id, updatedPatient));
    }
}
