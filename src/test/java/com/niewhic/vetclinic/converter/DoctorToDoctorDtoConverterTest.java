package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.doctor.DoctorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoctorToDoctorDtoConverterTest {

    private DoctorToDoctorDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DoctorToDoctorDtoConverter();
    }

    @ParameterizedTest
    @MethodSource("generateDoctors")
    void convert(Doctor doctor) {
        // When
        DoctorDto doctorDto = converter.convert(doctor);

        // Then
        assertAll(
                () -> assertNotNull(doctorDto),
                () -> assertEquals(doctor.getId(), doctorDto.getId()),
                () -> assertEquals(doctor.getName(), doctorDto.getName()),
                () -> assertEquals(doctor.getLastName(), doctorDto.getLastName()),
                () -> assertEquals(doctor.getRate(), doctorDto.getRate()),
                () -> assertEquals(doctor.getSpecialty(), doctorDto.getSpecialty()),
                () -> assertEquals(doctor.getAnimalSpecialty(), doctorDto.getAnimalSpecialty())
        );
    }

    private static Stream<Doctor> generateDoctors() {
        return Stream.of(
                Doctor.builder()
                        .id(1L)
                        .name("Dr. Smith")
                        .lastName("Smith")
                        .rate(5)
                        .specialty("Cardiology")
                        .animalSpecialty("Dogs")
                        .build(),
                Doctor.builder()
                        .id(2L)
                        .name("Dr. Jones")
                        .lastName("Jones")
                        .rate(4)
                        .specialty("Surgery")
                        .animalSpecialty("Cats")
                        .build()
        );
    }
}