package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable long id) {
        return patientService.findById(id);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable long id) {
        patientService.delete(id);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        return patientService.edit(id, patient);
    }

    @PatchMapping("/{id}")
    public Patient editPatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
        return patientService.editPartially(id, updatedPatient);
    }

//    @PostMapping("/addPatients")
//    public Patient addPatients(@RequestBody List<Patient> patients) {
//        return patients.forEach(patientService::save);
//    }
}
