package com.niewhic.vetclinic.model.doctor.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDoctorCommand {

    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Min(value = 1, message = "Rate must be greater than or equal to 1")
    @Max(value = 100, message = "Rate must be less than or equal to 100")
    private int rate;

    @NotBlank(message = "Specialty is mandatory")
    private String specialty;

    @NotBlank(message = "Animal specialty is mandatory")
    private String animalSpecialty;

}
