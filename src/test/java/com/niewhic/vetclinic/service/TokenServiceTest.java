package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.model.token.Token;
import com.niewhic.vetclinic.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AppointmentService appointmentService;
    private Token token;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        Patient patient = Patient.builder()
                .id(1L)
                .name("Puppy")
                .ownerName("Jan")
                .ownerLastName("Nowak")
                .dateOfBirth(LocalDate.now())
                .ownerEmail("jan@nowak.com")
                .species("species")
                .breed("breed")
                .build();
        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Piotr")
                .lastName("Kowalski")
                .NIP("123456")
                .rate(5000)
                .specialty("specialty")
                .animalSpecialty("animalSpecialty")
                .build();
        Office office = Office.builder()
                .id(1L)
                .type("type")
                .build();
        appointment = Appointment.builder()
                .id(1L)
                .doctor(doctor)
                .patient(patient)
                .dateTime(LocalDateTime.now())
                .notes("notes")
                .prescription("prescription")
                .office(office)
                .build();
        token = Token.builder()
                .id(1L)
                .token("token")
                .appointment(appointment)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
    }

    @Test
    void findByToken() {
        String tokenCode = "token";
        when(tokenRepository.findByToken(tokenCode)).thenReturn(token);

        Token resultToken = tokenService.findByToken(tokenCode);

        assertNotNull(resultToken);
        assertEquals("token", resultToken.getToken());
    }

    @Test
    void generate() {
        Token newToken = Token.builder()
                .id(1L)
                .token("token")
                .appointment(appointment)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
        when(tokenRepository.save(any(Token.class))).thenReturn(newToken);

        Token savedToken = tokenService.generate(appointment);

        assertEquals(newToken, savedToken);
    }

    @Test
    void deleteToken() {
        tokenService.deleteToken(token);

        verify(tokenRepository, times(1)).delete(token);
    }

    @Test
    void generateConfirmationUrl() {
        String expectedUrl = "http://localhost:8080/confirm-email?token=" + token.getToken();

        String confirmationUrl = tokenService.generateConfirmationUrl(token);

        assertNotNull(confirmationUrl);
        assertEquals(expectedUrl, confirmationUrl);
    }

    @Test
    void confirmEmail_correctly() {
        when(tokenService.findByToken("token")).thenReturn(token);

        boolean result = tokenService.confirmEmail("token");

        assertTrue(result);
        verify(appointmentService, times(1)).updateConfirmed(true, appointment.getId());
    }

    @Test
    void confirmEmail_invalidToken() {
        when(tokenService.findByToken(any(String.class))).thenReturn(null);

        boolean result = tokenService.confirmEmail("invalid_token");

        assertFalse(result);
    }

    @Test
    void confirmEmail_expiredToken() {
        token.setExpiryDate(LocalDateTime.now().minusHours(1));

        when(tokenService.findByToken("token")).thenReturn(token);

        boolean result = tokenService.confirmEmail("token");

        assertFalse(result);
    }
}