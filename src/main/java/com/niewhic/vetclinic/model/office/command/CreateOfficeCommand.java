package com.niewhic.vetclinic.model.office.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOfficeCommand {

    @NotNull(message = "Office type cannot be null")
    private String type;
}