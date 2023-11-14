package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.service.DoctorService;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateAppointmentCommandToAppointmentConverter {

    private final DoctorService doctorService;
    private final PatientService patientService;

    public Appointment convert(CreateAppointmentCommand command) {

        return Appointment.builder()
                .patient(patientService.findById(command.getPatientId()))
                .doctor(doctorService.findById(command.getDoctorId()))
                .notes(command.getNotes())
                .dateTime(command.getDateTime())
                .prescription(command.getPrescription())
                .build();
    }
}
