package com.niewhic.vetclinic.model.patient;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditPatientCommand {
    private String name;
    private String ownerName;
    private String ownerLastName;
    private String ownerEmail;
}

// TODO stworzyc konwerter