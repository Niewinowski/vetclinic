package com.niewhic.vetclinic.model.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreatePatientCommand {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Owner's name is mandatory")
    private String ownerName;
    @NotBlank(message = "Owner's last name is mandatory")
    private String ownerLastName;
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Owner's email is mandatory")
    private String ownerEmail;
    @NotBlank(message = "Species is mandatory")
    private String species;
    @NotBlank(message = "Breed is mandatory")
    private String breed;
}
