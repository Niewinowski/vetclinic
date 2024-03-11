package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender javaMailSender;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageCaptor;
    private Patient patient;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .ownerEmail("patient@email.com")
                .ownerName("John")
                .build();
        appointment = Appointment.builder()
                .id(1)
                .patient(patient)
                .build();
    }

    @Test
    void sendConfirmationEmailInEnglish() {
        // Given
        String language = "en";
        String tokenUrl = "http://example.com/confirm?token=xyz";
        String expectedSubject = "Appointment Confirmation";
        String expectedBody = "Dear John,\nPlease confirm your appointment by clicking the following link: http://example.com/confirm?token=xyz";

        // When
        emailService.sendConfirmationEmail(appointment, language, tokenUrl);

        // Then
        verify(javaMailSender, times(1)).send(simpleMailMessageCaptor.capture());
        SimpleMailMessage sentMessage = simpleMailMessageCaptor.getValue();

        assertThat(sentMessage.getSubject()).isEqualTo(expectedSubject);
        assertThat(sentMessage.getText()).isEqualTo(expectedBody);
    }

    @Test
    void sendConfirmationEmailInPolish() {
        // Given
        String language = "pl";
        String tokenUrl = "http://example.com/confirm?token=abc";
        String expectedSubject = "Potwierdzenie wizyty";
        String expectedBody = "Drogi John,\nProsze potwierdzic swoja wizyte, klikajac w ponizszy link: http://example.com/confirm?token=abc";

        // When
        emailService.sendConfirmationEmail(appointment, language, tokenUrl);

        // Then
        verify(javaMailSender, times(1)).send(simpleMailMessageCaptor.capture());
        SimpleMailMessage sentMessage = simpleMailMessageCaptor.getValue();

        assertThat(sentMessage.getSubject()).isEqualTo(expectedSubject);
        assertThat(sentMessage.getText()).isEqualTo(expectedBody);
    }
}