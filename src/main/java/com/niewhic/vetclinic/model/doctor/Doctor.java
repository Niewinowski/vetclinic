package com.niewhic.vetclinic.model.doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@NoArgsConstructor
@Builder
@Where(clause = "active=TRUE")
@SQLDelete(sql = "UPDATE Doctor SET active = false WHERE id=?")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private String NIP;
    private Integer rate;
    private String specialty;
    private String animalSpecialty;
    private boolean active = true;
}
