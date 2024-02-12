package com.niewhic.vetclinic.model.office.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditOfficeCommand {

    private String type;
}