package com.niewhic.vetclinic.model.doctor.command;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDoctorCommand {
    private String name;
    private String lastName;
    private int rate;
    private String specialty;
    private String animalSpecialty;
}
