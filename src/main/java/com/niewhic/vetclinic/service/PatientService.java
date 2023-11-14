package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.repository.PatientRepository;
import com.niewhic.vetclinic.model.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    // todo co to jest Bean springowy

    private final PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }

    public Patient save(Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    public void delete(long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public Patient edit(long id, Patient patient) {
        return patientRepository.findById(id)
                .map(patientToEdit -> {
                    patientToEdit.setId(id);
                    patientToEdit.setName(patient.getName());
                    patientToEdit.setBreed(patient.getBreed());
                    patientToEdit.setSpecies(patient.getSpecies());
                    patientToEdit.setOwnerEmail(patient.getOwnerEmail());
                    patientToEdit.setOwnerName(patient.getOwnerName());
                    patientToEdit.setOwnerLastName(patient.getOwnerLastName());
                    patientToEdit.setDateOfBirth(patient.getDateOfBirth());
                    return patientToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }

    @Transactional
    public Patient editPartially(long id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(patientToEdit -> {
                    Optional.ofNullable(updatedPatient.getName()).ifPresent(i -> patientToEdit.setName(updatedPatient.getName()));
                    Optional.ofNullable(updatedPatient.getOwnerName()).ifPresent(i -> patientToEdit.setOwnerName(updatedPatient.getOwnerName()));
                    Optional.ofNullable(updatedPatient.getOwnerLastName()).ifPresent(i -> patientToEdit.setOwnerLastName(updatedPatient.getOwnerLastName()));
                    Optional.ofNullable(updatedPatient.getDateOfBirth()).ifPresent(i -> patientToEdit.setDateOfBirth(updatedPatient.getDateOfBirth()));
                    Optional.ofNullable(updatedPatient.getOwnerEmail()).ifPresent(i -> patientToEdit.setOwnerEmail(updatedPatient.getOwnerEmail()));
                    Optional.ofNullable(updatedPatient.getSpecies()).ifPresent(i -> patientToEdit.setSpecies(updatedPatient.getSpecies()));
                    Optional.ofNullable(updatedPatient.getBreed()).ifPresent(i -> patientToEdit.setBreed(updatedPatient.getBreed()));
                    return patientToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }
}
