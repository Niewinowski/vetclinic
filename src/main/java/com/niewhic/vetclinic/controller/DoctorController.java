package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")

public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Void> addDoctor(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable long id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.edit(id, doctor));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Doctor> editDoctor(@PathVariable long id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.editPartially(id, doctor));
    }
}

//    @GetMapping
//    public Collection<Doctor> getAllDoctors() {
//        return doctorService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public Doctor getDoctorById(@PathVariable("id") long id) {
//        return doctorService.getById(id);
//    }
//
//    @PostMapping
//    public void addDoctor(@RequestBody Doctor doctor) {
//        doctorService.add(doctor);
//    }
//
//    @PostMapping("/addDoctors")
//    public void addDoctors(@RequestBody List<Doctor> doctors) {
//        doctors.forEach(doctorService::add);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteDoctor(@PathVariable("id") long id) {
//        doctorService.delete(id);
//    }
//
//    @PutMapping("/{id}")
//    public void updateDoctor(@PathVariable("id") long id, @RequestBody Doctor doctor) {
//        doctorService.update(id, doctor);
//    }
//
//    @PatchMapping("/{id}")
//    public void editDoctor(@PathVariable("id") long id, @RequestBody Doctor doctor) {
//        doctorService.edit(id, doctor);
//    }
//
//}
