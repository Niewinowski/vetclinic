package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.doctor.CreateDoctorCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import com.niewhic.vetclinic.model.doctor.EditDoctorCommand;
import com.niewhic.vetclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")

public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorService.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(modelMapper.map(doctor, DoctorDto.class));
    }

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody CreateDoctorCommand command) {
        Doctor doctor = modelMapper.map(command, Doctor.class);
        Doctor savedDoctor = doctorService.save(doctor);
        DoctorDto doctorDto = modelMapper.map(savedDoctor, DoctorDto.class);
        return new ResponseEntity<>(doctorDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable long id, @RequestBody EditDoctorCommand command) {
        Doctor doctorToUpdate = modelMapper.map(command, Doctor.class);
        doctorToUpdate.setId(id);
        Doctor updatedDoctor = doctorService.edit(id, doctorToUpdate);
        return ResponseEntity.ok(modelMapper.map(updatedDoctor, DoctorDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> editDoctor(@PathVariable long id, @RequestBody EditDoctorCommand command) {
        Doctor existingDoctor = doctorService.findById(id);
        if (command.getName() != null) {
            existingDoctor.setName(command.getName());
        }
        if (command.getLastName() != null) {
            existingDoctor.setLastName(command.getLastName());
        }
        if (command.getSpecialty() != null) {
            existingDoctor.setSpecialty(command.getSpecialty());
        }
        if (command.getAnimalSpecialty() != null) {
            existingDoctor.setAnimalSpecialty(command.getAnimalSpecialty());
        }
        if (command.getRate() != 0) {
            existingDoctor.setRate(command.getRate());
        }
        Doctor updatedDoctor = doctorService.save(existingDoctor);

        DoctorDto doctorDto = modelMapper.map(updatedDoctor, DoctorDto.class);

        return ResponseEntity.ok(doctorDto);
    }
}
