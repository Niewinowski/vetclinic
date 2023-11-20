package com.niewhic.vetclinic.model.doctor;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private long id;
    private String name;
    private String lastName;
    private int rate;
    private String specialty;
    private String animalSpecialty;
}