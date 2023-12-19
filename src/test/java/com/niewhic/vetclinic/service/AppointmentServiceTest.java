package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.AppointmentNotFoundException;
import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.appointment.EditAppointmentCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    Patient patient;
    Doctor doctor;
    Appointment appointment;
    @InjectMocks
    AppointmentService appointmentService;
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .id(1L)
                .name("Puppy")
                .ownerName("Jan")
                .ownerLastName("Nowak")
                .dateOfBirth(LocalDateTime.now())
                .ownerEmail("jan@nowak.com")
                .species("species")
                .breed("breed")
                .build();
        doctor = Doctor.builder()
                .id(1L)
                .name("Piotr")
                .lastName("Kowalski")
                .NIP("123456")
                .rate(5000)
                .specialty("specialty")
                .animalSpecialty("animalSpecialty")
                .build();
        appointment = Appointment.builder()
                .id(1L)
                .doctor(doctor)
                .patient(patient)
                .dateTime(LocalDateTime.now())
                .notes("notes")
                .prescription("prescription")
                .build();
    }

    @Test
    void givenAppointmentList_whenFindAll_thenReturnAppointmentList() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));
        List<Appointment> appointments = appointmentService.findAll();
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
    }

    @Test
    void givenAppointmentId_whenFindById_thenReturnAppointmentOptional() {
        long id = 1;
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
        Appointment currentAppointment = appointmentService.findById(id);
        assertNotNull(currentAppointment);
        assertEquals(1, currentAppointment.getPatient().getId());
        assertEquals(1, currentAppointment.getDoctor().getId());
    }

    @Test
    void givenNonExistingId_whenFindById_thenThrowsException() {
        long id = 10;
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.findById(id));
    }

    @Test
    void givenCreateCommand_whenSave_thenReturnAppointmentObject() {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.now())
                .notes("notes")
                .prescription("prescription")
                .build();

        when(modelMapper.map(command, Appointment.class)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment appointmentToSave = modelMapper.map(command, Appointment.class);
        Appointment resultAppointment = appointmentService.save(command);

        assertNotNull(appointmentToSave);
        assertNotNull(resultAppointment);
        assertEquals(1, resultAppointment.getDoctor().getId());
        assertEquals(1, resultAppointment.getPatient().getId());
    }

    @Test
    void givenAppointmentId_whenDelete_thenDeleteAppointmentObject() {
        long id = 1;
        appointmentService.delete(id);
        verify(appointmentRepository, times(1)).deleteById(id);
    }

    @Test
    void givenAppointmentIdAndCreateCommand_whenEdit_thenReturnAppointmentObject() {
        long id = 1;
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.now())
                .notes("editedNotes")
                .prescription("prescription")
                .build();
        Appointment updatedAppointment = Appointment.builder()
                .id(1L)
                .doctor(doctor)
                .patient(patient)
                .dateTime(LocalDateTime.now())
                .notes("editedNotes")
                .prescription("prescription")
                .build();
        when(modelMapper.map(command, Appointment.class)).thenReturn(updatedAppointment);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));

        Appointment mappedAppointment = modelMapper.map(command, Appointment.class);
        Appointment editedAppointment = appointmentService.edit(id, command);

        assertNotNull(mappedAppointment);
        assertAll(
                () -> assertEquals(updatedAppointment.getId(), editedAppointment.getId()),
                () -> assertEquals(updatedAppointment.getDoctor(), editedAppointment.getDoctor()),
                () -> assertEquals(updatedAppointment.getPatient(), editedAppointment.getPatient()),
                () -> assertEquals(updatedAppointment.getDateTime(), editedAppointment.getDateTime()),
                () -> assertEquals(updatedAppointment.getNotes(), editedAppointment.getNotes()),
                () -> assertEquals(updatedAppointment.getPrescription(), editedAppointment.getPrescription())
        );
    }

    @Test
    void givenNonExistingAppointmentId_whenEdit_thenThrowsException() {
        long id = 10;
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .dateTime(LocalDateTime.now())
                .notes("editedNotes")
                .prescription("prescription")
                .build();
        Appointment updatedAppointment = Appointment.builder()
                .id(1L)
                .doctor(doctor)
                .patient(patient)
                .dateTime(LocalDateTime.now())
                .notes("editedNotes")
                .prescription("prescription")
                .build();
        when(modelMapper.map(command, Appointment.class)).thenReturn(updatedAppointment);
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.edit(id, command));
    }

    @Test
    void givenAppointmentIdAndCreateCommand_whenEditPartially_thenReturnAppointmentObject() {
        long id = 1;
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .notes("editedNotes")
                .prescription("editedPrescription")
                .build();
        Appointment updatedAppointment = Appointment.builder()
                .id(1L)
                .doctor(null)
                .patient(patient)
                .dateTime(null)
                .notes("editedNotes")
                .prescription("editedPrescription")
                .build();
        when(modelMapper.map(command, Appointment.class)).thenReturn(updatedAppointment);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));

        Appointment editedAppointment = appointmentService.edit(id, command);
        verify(appointmentRepository, times(1)).findById(id);

        assertAll(
                () -> assertEquals(appointment.getId(), editedAppointment.getId()),
                () -> assertEquals(appointment.getDoctor(), editedAppointment.getDoctor()),
                () -> assertEquals(updatedAppointment.getPatient(), editedAppointment.getPatient()),
                () -> assertEquals(appointment.getDateTime(), editedAppointment.getDateTime()),
                () -> assertEquals(updatedAppointment.getNotes(), editedAppointment.getNotes()),
                () -> assertEquals(updatedAppointment.getPrescription(), editedAppointment.getPrescription())
        );
    }

    @Test
    void givenNonExistingAppointmentId_whenEditPartially_thenReturnAppointmentObject() {
        long id = 10;
        EditAppointmentCommand command = EditAppointmentCommand.builder()
                .doctorId(1L)
                .notes("editedNotes")
                .build();
        Appointment updatedAppointment = Appointment.builder()
                .id(1L)
                .doctor(null)
                .patient(patient)
                .dateTime(null)
                .notes("editedNotes")
                .prescription(null)
                .build();
        when(modelMapper.map(command, Appointment.class)).thenReturn(updatedAppointment);
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.editPartially(id, command));
    }
}