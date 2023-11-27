package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import com.niewhic.vetclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")

public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(modelMapper.map(doctor, DoctorDto.class));
    }
    // TODO zwracac DoctorDto, przyjmowac CreateDoctorCommand
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.save(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    // TODO zwracac DoctorDto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // TODO zwracac DoctorDto, przyjmowac EditDoctorCommand
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable long id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.edit(id, doctor));
    }
    // TODO zwracac DoctorDto, przyjmowac EditDoctorCommand
    @PatchMapping("/{id}")
    public ResponseEntity<Doctor> editDoctor(@PathVariable long id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.editPartially(id, doctor));
    }
}
