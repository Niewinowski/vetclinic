package com.niewhic.vetclinic.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.model.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Where(clause = "active = true")
@SQLDelete(sql = "UPDATE Appointment SET active = false WHERE id=?")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;
    @Builder.Default
    private boolean active = true;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Token> tokens = new HashSet<>();
    @Builder.Default
    private boolean confirmed = false;
}
