package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.AppointmentNotFoundException;
import com.niewhic.vetclinic.exception.DateIsBusyException;
import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;;
import com.niewhic.vetclinic.model.appointment.command.EditAppointmentCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static Logger LOGGER = LogManager.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", id)));
    }

    public Appointment save(CreateAppointmentCommand command) {
        Appointment appointmentToSave = modelMapper.map(command, Appointment.class);
        Doctor doctor = appointmentToSave.getDoctor();
        for (Appointment appointment : findByDoctorId(doctor.getId())) {
            if (appointmentToSave.getDateTime().isBefore(appointment.getDateTime().plusMinutes(16)) &&
                    appointmentToSave.getDateTime().isAfter(appointment.getDateTime().minusMinutes(15))) {
                throw new DateIsBusyException("Doctor has other appointment in this time.");
            }
        }
        Patient patient = appointmentToSave.getPatient();
        for (Appointment appointment : findByPatientId(patient.getId())) {
            if (appointmentToSave.getDateTime().isBefore(appointment.getDateTime().plusMinutes(16)) &&
                    appointmentToSave.getDateTime().isAfter(appointment.getDateTime().minusMinutes(15))) {
                throw new DateIsBusyException("Patient has other appointment in this time.");
            }
        }
        return appointmentRepository.save(appointmentToSave);
    }

    public void delete(long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public Appointment edit(long id, EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = modelMapper.map(updatedCommand, Appointment.class);
        return appointmentRepository.findById(id)
                .map(appointmentToEdit -> {
                    appointmentToEdit.setDoctor(updatedAppointment.getDoctor());
                    appointmentToEdit.setDateTime(updatedAppointment.getDateTime());
                    appointmentToEdit.setNotes(updatedAppointment.getNotes());
                    appointmentToEdit.setPrescription(updatedAppointment.getPrescription());
                    return appointmentToEdit;
                }).orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", id)));
    }

    @Transactional
    public Appointment editPartially(long id, EditAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = modelMapper.map(updatedCommand, Appointment.class);
        return appointmentRepository.findById(id)
                .map(appointmentToEdit -> {
                    Optional.ofNullable(updatedAppointment.getDoctor()).ifPresent(appointmentToEdit::setDoctor);
                    Optional.ofNullable(updatedAppointment.getDateTime()).ifPresent(appointmentToEdit::setDateTime);
                    Optional.ofNullable(updatedAppointment.getNotes()).ifPresent(appointmentToEdit::setNotes);
                    Optional.ofNullable(updatedAppointment.getPrescription()).ifPresent(appointmentToEdit::setPrescription);
                    return appointmentToEdit;
                }).orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", id)));
    }

    public List<Appointment> findByDoctorId(long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> findByPatientId(long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Transactional
    public void updateConfirmed(boolean confirmed, long appointmentId) {
        appointmentRepository.updateConfirmed(confirmed, appointmentId);
    }
}
