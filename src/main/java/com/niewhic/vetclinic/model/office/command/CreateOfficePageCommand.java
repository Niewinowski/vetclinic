package com.niewhic.vetclinic.model.office.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfficePageCommand {

    private int pageNumber = 0;
    private int pageSize = 5;

    @Pattern(regexp = "ASC|DESC", message = "INVALID_SORT_DIRECTION_VALUE")
    private String sortDirection = "ASC";

    @NotBlank(message = "Sort by cannot be blank")
    @Pattern(regexp = "id|type", message = "INVALID_SORT_BY_VALUE")
    private String sortBy = "id";
}