package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.model.patient.PatientDto;
import org.springframework.stereotype.Component;

@Component
public class PatientToPatientDtoConverter {

    public PatientDto convert(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .species(patient.getSpecies())
                .breed(patient.getBreed())
                .ownerName(patient.getOwnerName())
                .ownerLastName(patient.getOwnerLastName())
                .ownerEmail(patient.getOwnerEmail())
                .dateOfBirth(patient.getDateOfBirth())
                .build();
    }
}