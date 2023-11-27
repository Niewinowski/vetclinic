package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorToDoctorDtoConverter {

    public DoctorDto convert(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .lastName(doctor.getLastName())
                .rate(doctor.getRate())
                .specialty(doctor.getSpecialty())
                .animalSpecialty(doctor.getAnimalSpecialty())
                .build();
    }
}