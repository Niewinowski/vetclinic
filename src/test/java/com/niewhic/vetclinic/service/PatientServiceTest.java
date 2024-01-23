package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.PatientNotFoundException;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;
    private Patient patient;
    @Mock
    private PatientRepository patientRepository;
    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .id(1L)
                .name("name")
                .ownerName("ownerName")
                .ownerLastName("ownerLastName")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();
    }

    @Test
    void givenPatientsList_whenFindAll_thenReturnPatientsList() {
        // given
        Patient patient1 = Patient.builder()
                .id(2L)
                .name("name")
                .ownerName("ownerName")
                .ownerLastName("ownerLastName")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();

        when(patientRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(patient, patient1)));

        // when
        Page<Patient> patientPage = patientService.findAll(PageRequest.of(0, 5));

        // then
        assertNotNull(patientPage);
        assertEquals(2, patientPage.getTotalElements());
    }

    @Test
    void givenPatientId_whenFindById_thenReturnPatientObject() {
        // given
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // when
        Patient currentPatient = patientService.findById(patient.getId());

        // then
        assertNotNull(currentPatient);
    }

    @Test
    void givenNonExistingPatientId_whenFindById_thenThrowsException() {
        long id = 100;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.findById(id));
    }

    @Test
    void givenPatientObject_whenSave_thenReturnPatientObject() {
        Patient newPatient = Patient.builder()
                .id(3L)
                .name("name1")
                .ownerName("ownerName1")
                .ownerLastName("ownerLastName")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();

        when(patientRepository.save(newPatient)).thenReturn(newPatient);

        Patient resultPatient = patientService.save(newPatient);

        assertEquals(newPatient, resultPatient);
    }

    @Test
    void givenPatientId_whenDelete_thenDeletePatientObject() {
        long id = 1;

        patientService.delete(id);

        verify(patientRepository, times(1)).deleteById(id);
    }

    @Test
    void givenPatientIdAndPatientObject_whenEdit_thenReturnPatientObject() {
        long id = 1;
        Patient updatedPatient = Patient.builder()
                .id(id)
                .name("name1")
                .ownerName("ownerName")
                .ownerLastName("ownerLastName")
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        Patient editedPatient = patientService.edit(id, updatedPatient);

        assertAll(
                () -> assertEquals(updatedPatient.getName(), editedPatient.getName()),
                () -> assertEquals(updatedPatient.getOwnerName(), editedPatient.getOwnerName()),
                () -> assertEquals(updatedPatient.getOwnerLastName(), editedPatient.getOwnerLastName()),
                () -> assertEquals(updatedPatient.getOwnerEmail(), editedPatient.getOwnerEmail()),
                () -> assertEquals(updatedPatient.getSpecies(), editedPatient.getSpecies()),
                () -> assertEquals(updatedPatient.getBreed(), editedPatient.getBreed())
        );
    }

    @Test
    void givenNonExistingPatientId_whenEdit_thenThrowsException() {
        long id = 1;
        Patient updatedPatient = Patient.builder()
                .id(2L)
                .name("name1")
                .ownerName("ownerName")
                .ownerLastName("ownerLastName")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.edit(id, updatedPatient));
    }

    @Test
    void givenPatientIdAndPatientObject_whenEditPartially_thenReturnPatientObject() {
        long id = 1;
        Patient updatedPatient = Patient.builder()
                .id(id)
                .name("name2")
                .ownerName("ownerName2")
                .ownerLastName("ownerLastName2")
                .dateOfBirth(null)
                .ownerEmail("ownerEmail2")
                .species(null)
                .breed(null)
                .build();

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        Patient editedPatient = patientService.editPartially(id, updatedPatient);

        assertAll(
                () -> assertEquals(updatedPatient.getName(), editedPatient.getName()),
                () -> assertEquals(updatedPatient.getOwnerName(), editedPatient.getOwnerName()),
                () -> assertEquals(updatedPatient.getOwnerLastName(), editedPatient.getOwnerLastName()),
                () -> assertEquals(patient.getDateOfBirth(), editedPatient.getDateOfBirth()),
                () -> assertEquals(updatedPatient.getOwnerEmail(), editedPatient.getOwnerEmail()),
                () -> assertEquals(patient.getSpecies(), editedPatient.getSpecies()),
                () -> assertEquals(patient.getBreed(), editedPatient.getBreed())
        );
    }

    @Test
    void givenNonExistingPatientId_whenEditPartially_thenThrowsException() {
        long id = 1;
        Patient updatedPatient = Patient.builder()
                .id(2L)
                .name("name1")
                .ownerName("ownerName")
                .ownerLastName("ownerLastName")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("ownerEmail")
                .species("species")
                .breed("breed")
                .build();

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.editPartially(id, updatedPatient));
    }

}