package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void addDoctor() {
        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("John")
                .lastName("Smith")
                .rate(5)
                .specialty("Cardiology")
                .animalSpecialty("Dogs")
                .build();
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        Doctor savedDoctor = doctorService.save(doctor);
        assertAll(
                () -> assertNotNull(savedDoctor),
                () -> assertEquals(doctor.getId(), savedDoctor.getId()),
                () -> assertEquals(doctor.getName(), savedDoctor.getName()),
                () -> assertEquals(doctor.getSpecialty(), savedDoctor.getSpecialty())
        );
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void findDoctorById() {
        long doctorId = 1L;
        Doctor doctor = Doctor.builder()
                .id(doctorId)
                .name("John")
                .specialty("Cardiology")
                .build();
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        Doctor foundDoctor = doctorService.findById(doctorId);
        assertAll(
                () -> assertNotNull(foundDoctor),
                () -> assertEquals(doctorId, foundDoctor.getId()),
                () -> assertEquals("John", foundDoctor.getName())
        );
        verify(doctorRepository, times(1)).findById(doctorId);
    }

    @Test
    void deleteDoctor() {
        long doctorId = 1L;
        doNothing().when(doctorRepository).deleteById(doctorId);
        doctorService.delete(doctorId);
        verify(doctorRepository, times(1)).deleteById(doctorId);
    }
}