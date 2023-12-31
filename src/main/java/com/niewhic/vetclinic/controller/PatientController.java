package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.patient.CreatePatientCommand;
import com.niewhic.vetclinic.model.patient.EditPatientCommand;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.model.patient.PatientDto;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patientDtoList = patientService.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
        return ResponseEntity.ok(patientDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable long id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity.ok(modelMapper.map(patient, PatientDto.class));
    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@RequestBody CreatePatientCommand command) {
        Patient savedPatient = patientService.save(modelMapper.map(command, Patient.class));
        return new ResponseEntity<>(modelMapper.map(savedPatient, PatientDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable long id, @RequestBody EditPatientCommand command) {
        Patient updatedPatient = patientService.edit(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.edit(id, updatedPatient), PatientDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDto> editPatient(@PathVariable long id, @RequestBody EditPatientCommand command) {
        Patient editedPatient = patientService.editPartially(id, modelMapper.map(command, Patient.class));
        return ResponseEntity.ok(modelMapper.map(patientService.editPartially(id, editedPatient), PatientDto.class));
    }
}
