package com.niewhic.vetclinic.model.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String ownerName;
    private String ownerLastName;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDate dateOfBirth;
    private String ownerEmail;
    private String species;
    private String breed;
}
