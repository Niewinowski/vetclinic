package com.niewhic.vetclinic.model.appointment;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class AppointmentDto {

    private long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;


}
