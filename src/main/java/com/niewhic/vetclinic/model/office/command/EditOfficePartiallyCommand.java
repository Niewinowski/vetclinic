package com.niewhic.vetclinic.model.office.command;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditOfficePartiallyCommand {

    private String type;
}
