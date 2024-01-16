package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    public Page<Doctor> findAll(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    public Doctor findById(long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Doctor with id %s not found", id)));
    }

    public Doctor save(Doctor newDoctor) {
        return doctorRepository.save(newDoctor);
    }

    public void delete(long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public Doctor edit(long id, Doctor doctor) {
        return doctorRepository.findById(id)
                .map(doctorToEdit -> {
                    doctorToEdit.setId(id);
                    doctorToEdit.setName(doctor.getName());
                    doctorToEdit.setLastName(doctor.getLastName());
                    doctorToEdit.setNIP(doctor.getNIP());
                    doctorToEdit.setRate(doctor.getRate());
                    doctorToEdit.setSpecialty(doctor.getSpecialty());
                    doctorToEdit.setAnimalSpecialty(doctor.getAnimalSpecialty());
                    return doctorToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Doctor with id %s not found", id)));
    }


    @Transactional
    public Doctor editPartially(long id, Doctor updatedDoctor) {
        return doctorRepository.findById(id)
                .map(doctorToEdit -> {
                    Optional.ofNullable(updatedDoctor.getName()).ifPresent(doctorToEdit::setName);
                    Optional.ofNullable(updatedDoctor.getLastName()).ifPresent(doctorToEdit::setLastName);
                    Optional.ofNullable(updatedDoctor.getNIP()).ifPresent(doctorToEdit::setNIP);
                    Optional.ofNullable(updatedDoctor.getRate()).ifPresent(doctorToEdit::setRate);
                    Optional.ofNullable(updatedDoctor.getSpecialty()).ifPresent(doctorToEdit::setSpecialty);
                    Optional.ofNullable(updatedDoctor.getAnimalSpecialty()).ifPresent(doctorToEdit::setAnimalSpecialty);
                    return doctorToEdit;
                })
                .orElseThrow(() -> new NoSuchElementException(String.format("Doctor with id %s not found", id)));
    }


}
