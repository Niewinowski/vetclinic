package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.service.EmailService;
import com.niewhic.vetclinic.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmEmailController {

    private final EmailService emailService;
    private final MessageService messageService; // Załóżmy, że to jest serwis opisany wyżej

    @Autowired
    public ConfirmEmailController(EmailService emailService, MessageService messageService) {
        this.emailService = emailService;
        this.messageService = messageService;
    }

    @GetMapping("/confirmEmail/{appointmentId}/{language}")
    public String confirmEmail(@PathVariable Long appointmentId, @PathVariable String language) {
        // Tutaj logika do pobrania danych potrzebnych do wiadomości e-mail
        String patientName = "Jan Kowalski";
        String confirmationLink = "http://jakStrona.pl/confirm?appointmentId=" + appointmentId;

        String emailBody = messageService.getConfirmationEmailBody(language, patientName, confirmationLink);
        String subject = messageService.getSubject(language, "appointment.confirmation.subject");

        // Do wyslania emaila ???
        //emailService.sendEmail(patientName, subject, emailBody);

        return "Email sent";
    }
}