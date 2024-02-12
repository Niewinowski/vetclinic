package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.service.EmailService;
import com.niewhic.vetclinic.service.MessageService;
import com.niewhic.vetclinic.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm-email")
public class ConfirmEmailController {

    private final EmailService emailService;
//    private final MessageService messageService; // Załóżmy, że to jest serwis opisany wyżej
    private final TokenService tokenService;


//    @GetMapping("/confirmEmail/{appointmentId}/{language}")
//    public String confirmEmail2(@PathVariable Long appointmentId, @PathVariable String language) {
//        // Tutaj logika do pobrania danych potrzebnych do wiadomości e-mail
//        String patientName = "Jan Kowalski";
//        String confirmationLink = "http://jakStrona.pl/confirm?appointmentId=" + appointmentId;
//
//        String emailBody = messageService.getConfirmationEmailBody(language, patientName, confirmationLink);
//        String subject = messageService.getSubject(language, "appointment.confirmation.subject");
//
//        // Do wyslania emaila ???
//        //emailService.sendEmail(patientName, subject, emailBody);
//
//        return "Email sent";
//    }

    @GetMapping
    public ResponseEntity<Void> confirmEmail(@RequestParam("token") String token) {
        if (tokenService.confirmEmail(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}