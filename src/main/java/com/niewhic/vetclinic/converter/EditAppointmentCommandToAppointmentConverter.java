package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.command.EditAppointmentCommand;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
@RequiredArgsConstructor
@Component
public class EditAppointmentCommandToAppointmentConverter implements Converter<EditAppointmentCommand, Appointment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditAppointmentCommandToAppointmentConverter.class);
    private final DoctorService doctorService;
    @Override
    public Appointment convert(MappingContext<EditAppointmentCommand, Appointment> mappingContext) {
        EditAppointmentCommand command = mappingContext.getSource();
        try {
            Appointment appointment = Appointment.builder()
                    .patient(Patient.builder().build())
                    .doctor(command.getDoctorId() != null ? doctorService.findById(command.getDoctorId()) : null)
                    .notes(command.getNotes())
                    .dateTime(command.getDateTime())
                    .prescription(command.getPrescription())
                    .build();

            LOGGER.info("Successfully converted EditAppointmentCommand to Appointment");
            return appointment;
        } catch (NoSuchElementException e) {
            LOGGER.error("Error converting EditAppointmentCommand to Appointment: {}", e.getMessage());
            throw e;
        }
    }
}

