package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.appointment.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendConfirmationEmail(Appointment appointment, String language, String tokenUrl) {
        Locale locale = new Locale(language);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        String subject = messages.getString("appointment.confirmation.subject");
        String bodyTemplate = messages.getString("appointment.confirmation.body");

        String body = MessageFormat.format(bodyTemplate, appointment.getPatient().getOwnerName(), tokenUrl);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointment.getPatient().getOwnerEmail());
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
