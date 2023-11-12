package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @GetMapping
    public Collection<Doctor> getAllDoctors() {
        return doctorRepository.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable("id") long id) {
        return doctorRepository.getById(id);
    }

    @PostMapping
    public void addDoctor(@RequestBody Doctor doctor) {
        doctorRepository.add(doctor);
    }

    @PostMapping("/addDoctors")
    public void addDoctors(@RequestBody List<Doctor> doctors) {
        doctors.forEach(doctorRepository::add);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable("id") long id) {
        doctorRepository.delete(id);
    }

    @PutMapping("/{id}")
    public void updateDoctor(@PathVariable("id") long id, @RequestBody Doctor doctor) {
        doctorRepository.update(id, doctor);
    }

    @PatchMapping("/{id}")
    public void editDoctor(@PathVariable("id") long id, @RequestBody Doctor doctor) {
        doctorRepository.edit(id, doctor);
    }

}
