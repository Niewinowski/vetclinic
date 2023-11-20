package com.niewhic.vetclinic.model.patient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PatientDto {
    private long id;
    private String name;
    private String ownerName;
    private String ownerLastName;
    private LocalDateTime dateOfBirth;
    private String ownerEmail;
    private String species;
    private String breed;
}
