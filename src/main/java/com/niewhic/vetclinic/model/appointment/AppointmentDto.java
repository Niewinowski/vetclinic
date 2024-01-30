package com.niewhic.vetclinic.model.appointment;

import com.niewhic.vetclinic.model.doctor.DoctorDto;
import com.niewhic.vetclinic.model.office.OfficeDto;
import com.niewhic.vetclinic.model.patient.PatientDto;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private long id;
    private DoctorDto doctor;
    private PatientDto patient;
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;
    private OfficeDto office;


}
