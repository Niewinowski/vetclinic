//package com.niewhic.vetclinic.controller;
//
//import com.niewhic.vetclinic.model.doctor.Doctor;
//import com.niewhic.vetclinic.service.DoctorService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//import java.util.List;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/doctors")
//public class DoctorController {
//
//    private final DoctorService doctorService;
//
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
