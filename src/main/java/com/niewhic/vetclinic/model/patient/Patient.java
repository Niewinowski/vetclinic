package com.niewhic.vetclinic.model.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Patient {
    private long id;
    private String name;
    private String ownerName;
    private String ownerLastName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
    private String ownerEmail;
    private String species;
    private String breed;
}
