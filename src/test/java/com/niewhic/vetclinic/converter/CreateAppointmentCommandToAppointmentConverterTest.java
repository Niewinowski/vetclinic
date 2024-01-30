package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.service.DoctorService;
import com.niewhic.vetclinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreateAppointmentCommandToAppointmentConverterTest {

    private CreateAppointmentCommandToAppointmentConverter testedConverter;
    @Mock
    private MappingContext<CreateAppointmentCommand, Appointment> mappingContext;
    @Mock
    private DoctorService doctorService;
    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        testedConverter = new CreateAppointmentCommandToAppointmentConverter(doctorService, patientService);
    }

    @ParameterizedTest
    @MethodSource("generateCreateAppointmentCommands")
    void convert(CreateAppointmentCommand command) {
        // Given
        Patient patient = Patient.builder()
                .id(command.getPatientId())
                .build();

        Doctor doctor = Doctor.builder()
                .id(command.getDoctorId())
                .build();

        when(patientService.findById(patient.getId())).thenReturn(patient);
        when(doctorService.findById(doctor.getId())).thenReturn(doctor);
        when(mappingContext.getSource()).thenReturn(command);

        // when

        Appointment appointment = testedConverter.convert(mappingContext);

        //Then
        assertAll(
                () -> assertNotNull(appointment),
                () -> assertInstanceOf(Appointment.class, appointment),
                () -> assertEquals(appointment.getPatient().getId(), command.getPatientId()),
                () -> assertEquals(appointment.getDoctor().getId(), command.getDoctorId()),
                () -> assertEquals(appointment.getDateTime(), command.getDateTime()),
                () -> assertEquals(appointment.getPrescription(), command.getPrescription()),
                () -> assertEquals(appointment.getNotes(), command.getNotes())
        );
    }

    private static Stream<CreateAppointmentCommand> generateCreateAppointmentCommands() {
        return Stream.of(
                CreateAppointmentCommand.builder()
                        .patientId(1L)
                        .doctorId(1L)
                        .dateTime(LocalDateTime.of(2022, 02, 02, 20,00,00))
                        .notes("testowo")
                        .prescription("cos")
                        .build(),
                CreateAppointmentCommand.builder()
                        .patientId(2L)
                        .doctorId(2L)
                        .dateTime(LocalDateTime.of(2023, 02, 02, 20,00,00))
                        .notes("testowo2")
                        .prescription("cos2")
                        .build(),
                CreateAppointmentCommand.builder()
                        .patientId(3L)
                        .doctorId(3L)
                        .build()
        );
    }

}