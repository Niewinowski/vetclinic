package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.AppointmentNotFoundException;
import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;;
import com.niewhic.vetclinic.model.appointment.EditAppointmentCommand;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {
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
}
