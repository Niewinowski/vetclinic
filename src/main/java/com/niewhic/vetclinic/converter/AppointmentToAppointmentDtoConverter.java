package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.AppointmentDto;
import com.niewhic.vetclinic.model.doctor.CreateDoctorDtoCommand;
import com.niewhic.vetclinic.model.patient.CreatePatientCommand;

public class AppointmentToAppointmentDtoConverter {
    private final DoctorToDoctorDtoConverter doctorConverter;
    private final PatientToPatientDtoConverter patientConverter;

    public AppointmentToAppointmentDtoConverter(DoctorToDoctorDtoConverter doctorConverter, PatientToPatientDtoConverter patientConverter) {
        this.doctorConverter = doctorConverter;
        this.patientConverter = patientConverter;
    }
    public AppointmentDto convert(Appointment appointment) {
        return AppointmentDto.builder()
                .doctor(doctorConverter.convert(appointment.getDoctor()))
                .patient(patientConverter.convert(appointment.getPatient()))
                .notes(appointment.getNotes())
                .dateTime(appointment.getDateTime())
                .prescription(appointment.getPrescription())
                .build();
    }
}
