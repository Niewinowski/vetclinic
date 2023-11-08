package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private PatientRepository patientRepository = new PatientRepository();
    // mappingi analogicznie jak w testcontrollerze
    @GetMapping
    public Collection<Patient> getAllPatients() {
        return patientRepository.getAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable long id) {
        return patientRepository.getById(id);
    }

    @PostMapping
    public void addPatient(@RequestBody Patient patient) {
        patientRepository.add(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable long id) {
        patientRepository.delete(id);
    }

    @PutMapping("/{id}")
    public void updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        patientRepository.update(id, patient);
    }

    @PatchMapping("/{id}")
    public void editPatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
        patientRepository.edit(id, updatedPatient);
    }
}
