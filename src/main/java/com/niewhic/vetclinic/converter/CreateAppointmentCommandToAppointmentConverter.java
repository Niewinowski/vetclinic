package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.service.DoctorService;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
public class CreateAppointmentCommandToAppointmentConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAppointmentCommandToAppointmentConverter.class);

    private final DoctorService doctorService;
    private final PatientService patientService;

    public Appointment convert(CreateAppointmentCommand command) {
        try {
            Appointment appointment = Appointment.builder()
                    .patient(patientService.findById(command.getPatientId()))
                    .doctor(doctorService.findById(command.getDoctorId()))
                    .notes(command.getNotes())
                    .dateTime(command.getDateTime())
                    .prescription(command.getPrescription())
                    .build();

            LOGGER.info("Successfully converted CreateAppointmentCommand to Appointment");
            return appointment;
        } catch (NoSuchElementException e) {
            LOGGER.error("Error converting CreateAppointmentCommand to Appointment: {}", e.getMessage());
            throw e;
        }
    }
    //    return Appointment.builder()
    //            .patient(patientService.findById(command.getPatientId()))
    //            .doctor(doctorService.findById(command.getDoctorId()))
    //            .notes(command.getNotes())
     //           .dateTime(command.getDateTime())
     //           .prescription(command.getPrescription())
     //           .build();
   // }
}
