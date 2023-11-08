package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.patient.Patient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PatientRepository {
    private Map<Long, Patient> patientMap = new HashMap<>();

    // metody na dodanie, znajdowanie, edycje itp jak bylo w testcontroller

    public Collection<Patient> getAll() {
        return patientMap.values();
    }

    public Patient getById(long id) {
        if (!patientMap.containsKey(id)) {
            throw new IllegalArgumentException("no patient with such id");
        }
        return patientMap.get(id);
    }

    public void add(Patient newPatient) {
        if (patientMap.containsKey(newPatient.getId())) {
            throw new IllegalArgumentException("patient exists");
        }
        patientMap.put(newPatient.getId(), newPatient);
    }

    public void delete(long id) {
        patientMap.remove(id);
    }

    public void update(long id, Patient patient) {
        patientMap.replace(id, patient);
    }

    public void edit(long id, Patient updatedPatient) {
        Patient patient = getById(id);
        Optional.ofNullable(updatedPatient.getName()).ifPresent(i -> patient.setName(updatedPatient.getName()));
        Optional.ofNullable(updatedPatient.getOwnerName()).ifPresent(i -> patient.setOwnerName(updatedPatient.getOwnerName()));
        Optional.ofNullable(updatedPatient.getOwnerLastName()).ifPresent(i -> patient.setOwnerLastName(updatedPatient.getOwnerLastName()));
        Optional.ofNullable(updatedPatient.getDateOfBirth()).ifPresent(i -> patient.setDateOfBirth(updatedPatient.getDateOfBirth()));
        Optional.ofNullable(updatedPatient.getOwnerEmail()).ifPresent(i -> patient.setOwnerEmail(updatedPatient.getOwnerEmail()));
        Optional.ofNullable(updatedPatient.getSpecies()).ifPresent(i -> patient.setSpecies(updatedPatient.getSpecies()));
        Optional.ofNullable(updatedPatient.getBreed()).ifPresent(i -> patient.setBreed(updatedPatient.getBreed()));
        patientMap.replace(id, patient);
    }
}
