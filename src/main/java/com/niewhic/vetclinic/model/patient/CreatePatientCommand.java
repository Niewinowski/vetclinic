package com.niewhic.vetclinic.model.patient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreatePatientCommand {
    private String name;
    private String ownerName;
    private String ownerLastName;
    private LocalDate dateOfBirth;
    private String ownerEmail;
    private String species;
    private String breed;
}
