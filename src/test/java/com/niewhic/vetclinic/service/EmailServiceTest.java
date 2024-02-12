package com.niewhic.vetclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender javaMailSender;
    private final String SUBJECT = "Test Subject";
    private final String BODY = "Test Body";

    @Test
    void sendEmail() {
        String to = "patient@email.com";

        emailService.sendEmail(to, SUBJECT, BODY);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}