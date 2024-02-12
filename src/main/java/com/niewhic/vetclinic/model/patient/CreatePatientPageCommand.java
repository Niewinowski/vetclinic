package com.niewhic.vetclinic.model.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientPageCommand {
    private int pageNumber = 0;
    private int pageSize = 5;
    private String sortDirection = "ASC";
    @Pattern(regexp = "id|name|ownerName|ownerLastName|dateOfBirth|ownerEmail|species|breed", message = "INVALID_SORT_BY_VALUE")
    @NotBlank(message = "Sort by cannot be blank")
    private String sortBy = "id";
}
