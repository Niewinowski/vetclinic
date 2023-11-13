package com.niewhic.vetclinic.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Appointment {
    // appointment(id, doctor, patient, localDateTime, notes, prescription)
    private long id;
    private Doctor doctor;
    private Patient patient;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;


}
