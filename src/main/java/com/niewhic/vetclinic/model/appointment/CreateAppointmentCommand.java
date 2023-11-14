package com.niewhic.vetclinic.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateAppointmentCommand {

    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;

}
