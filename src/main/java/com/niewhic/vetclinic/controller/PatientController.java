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
    // TODO zwracac PatientDto
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAll());
    }

    // TODO zwracac PatientDto
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        return ResponseEntity.ok(patientService.findById(id));
    }
    // TODO zwracac PatientDto, przyjmowac CreatePatientCommand
    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.save(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // TODO zwracac PatientDto, przyjmowac EditPatientCommand
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.edit(id, patient));
    }
    // TODO zwracac PatientDto, przyjmowac EditPatientCommand
    @PatchMapping("/{id}")
    public ResponseEntity<Patient> editPatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
        return ResponseEntity.ok(patientService.editPartially(id, updatedPatient));
    }
}
