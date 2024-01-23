package com.niewhic.vetclinic.model.doctor.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorPageCommand {

    private int pageNumber = 0;
    private int pageSize = 5;
    @Pattern(regexp = "ASC|DESC",
            message = "INVALID_SORT_DIRECTION_VALUE")
    private String sortDirection = "ASC";
    @Pattern(regexp = "id|name|lastName|NIP|rate|specialty|animalSpecialty",
             message = "INVALID_SORT_BY_VALUE")

    @NotBlank(message = "Sort by cannot be blank")
    private String sortBy = "id";
}
