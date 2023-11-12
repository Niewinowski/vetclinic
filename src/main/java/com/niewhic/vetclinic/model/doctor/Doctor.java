package com.niewhic.vetclinic.model.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Doctor {
    private long id;
    private String name;
    private String lastName;
    private String NIP;
    private int rate;
    private String specialty;
    private String animalSpecialty;
}
