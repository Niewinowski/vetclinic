package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.converter.CreateAppointmentCommandToAppointmentConverter;
import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CreateAppointmentCommandToAppointmentConverter converter;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Appointment with id %s not found", id)));
    }

    public Appointment save(CreateAppointmentCommand command) {
        return appointmentRepository.save(converter.convert(command));
    }

    public void delete(long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public Appointment edit(long id, CreateAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = converter.convert(updatedCommand);
        return appointmentRepository.findById(id)
                        .map(appointmentToEdit -> {
                            appointmentToEdit.setDoctor(updatedAppointment.getDoctor());
                            appointmentToEdit.setPatient(updatedAppointment.getPatient());
                            appointmentToEdit.setDateTime(updatedAppointment.getDateTime());
                            appointmentToEdit.setNotes(updatedAppointment.getNotes());
                            appointmentToEdit.setPrescription(updatedAppointment.getPrescription());
                            return appointmentToEdit;
                        }).orElseThrow(() -> new NoSuchElementException(String.format("Appointment with id %s not found", id)));
    }

    @Transactional
    public Appointment editPartially(long id, CreateAppointmentCommand updatedCommand) {
        Appointment updatedAppointment = converter.convert(updatedCommand);
        return appointmentRepository.findById(id)
                .map(appointmentToEdit -> {
                    Optional.ofNullable(updatedAppointment.getDoctor()).ifPresent(appointmentToEdit::setDoctor);
                    Optional.ofNullable(updatedAppointment.getPatient()).ifPresent(appointmentToEdit::setPatient);
                    Optional.ofNullable(updatedAppointment.getDateTime()).ifPresent(appointmentToEdit::setDateTime);
                    Optional.ofNullable(updatedAppointment.getNotes()).ifPresent(appointmentToEdit::setNotes);
                    Optional.ofNullable(updatedAppointment.getPrescription()).ifPresent(appointmentToEdit::setPrescription);
                    return appointmentToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Appointment with id %s not found", id)));
    }
}
