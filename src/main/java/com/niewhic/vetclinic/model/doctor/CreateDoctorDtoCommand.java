package com.niewhic.vetclinic.model.doctor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDoctorDtoCommand {
    private long id;
    private String name;
    private String lastName;
    private int rate;
    private String specialty;
    private String animalSpecialty;
}
