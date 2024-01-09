package com.niewhic.vetclinic.model.doctor;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDoctorPartiallyCommand {
    private Optional<String> name;
    private Optional<String> lastName;
    private Optional<Integer> rate;
    private Optional<String> specialty;
    private Optional<String> animalSpecialty;
}
