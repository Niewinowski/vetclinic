package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;
import com.niewhic.vetclinic.service.DoctorService;
import com.niewhic.vetclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
public class CreateAppointmentCommandToAppointmentConverter implements Converter<CreateAppointmentCommand, Appointment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAppointmentCommandToAppointmentConverter.class);
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Override
    public Appointment convert(MappingContext<CreateAppointmentCommand, Appointment> mappingContext) {
        CreateAppointmentCommand command = mappingContext.getSource();
        try {
            Appointment appointment = Appointment.builder()
                    .patient(command.getPatientId() != null ? patientService.findById(command.getPatientId()) : null)
                    .doctor(command.getDoctorId() != null ? doctorService.findById(command.getDoctorId()) : null)
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
}
