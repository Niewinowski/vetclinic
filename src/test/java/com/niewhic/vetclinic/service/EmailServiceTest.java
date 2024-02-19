package com.niewhic.vetclinic.service;

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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageCaptor;
    @Test
    void sendConfirmationEmailInEnglish() {
        // Given
        String to = "patient@email.com";
        String language = "en";
        String patientName = "John";
        Long appointmentId = 123L;
        String tokenUrl = "http://example.com/confirm?token=xyz";
        String expectedSubject = "Appointment Confirmation";
        String expectedBody = "Dear John,\nPlease confirm your appointment by clicking the following link: http://example.com/confirm?token=xyz";

        // When
        emailService.sendConfirmationEmail(to, language, patientName, appointmentId, tokenUrl);

        // Then
        verify(javaMailSender, times(1)).send(simpleMailMessageCaptor.capture());
        SimpleMailMessage sentMessage = simpleMailMessageCaptor.getValue();

        assertThat(sentMessage.getSubject()).isEqualTo(expectedSubject);
        assertThat(sentMessage.getText()).isEqualTo(expectedBody);
    }

    @Test
    void sendConfirmationEmailInPolish() {
        // Given
        String to = "patient@email.com";
        String language = "pl";
        String patientName = "Jan";
        Long appointmentId = 456L;
        String tokenUrl = "http://example.com/confirm?token=abc";
        String expectedSubject = "Potwierdzenie wizyty";
        String expectedBody = "Drogi Jan,\nProsze potwierdzic swoja wizyte, klikajac w ponizszy link: http://example.com/confirm?token=abc";

        // When
        emailService.sendConfirmationEmail(to, language, patientName, appointmentId, tokenUrl);

        // Then
        verify(javaMailSender, times(1)).send(simpleMailMessageCaptor.capture());
        SimpleMailMessage sentMessage = simpleMailMessageCaptor.getValue();

        assertThat(sentMessage.getSubject()).isEqualTo(expectedSubject);
        assertThat(sentMessage.getText()).isEqualTo(expectedBody);
    }
}