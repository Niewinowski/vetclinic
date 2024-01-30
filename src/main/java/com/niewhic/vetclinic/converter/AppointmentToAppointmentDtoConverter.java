package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.AppointmentDto;

public class AppointmentToAppointmentDtoConverter {
    private final DoctorToDoctorDtoConverter doctorConverter;
    private final PatientToPatientDtoConverter patientConverter;
    private final OfficeToOfficeDtoConverter officeConverter;

    public AppointmentToAppointmentDtoConverter(DoctorToDoctorDtoConverter doctorConverter, PatientToPatientDtoConverter patientConverter, OfficeToOfficeDtoConverter officeConverter) {
        this.doctorConverter = doctorConverter;
        this.patientConverter = patientConverter;
        this.officeConverter = officeConverter;
    }
    public AppointmentDto convert(Appointment appointment) {
        return AppointmentDto.builder()
                .doctor(doctorConverter.convert(appointment.getDoctor()))
                .patient(patientConverter.convert(appointment.getPatient()))
                .notes(appointment.getNotes())
                .dateTime(appointment.getDateTime())
                .prescription(appointment.getPrescription())
                .office(officeConverter.convert(appointment.getOffice()))
                .build();
    }
}
