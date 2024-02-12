package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.token.Token;
import com.niewhic.vetclinic.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final AppointmentService appointmentService;

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token generate(Appointment appointment) {
        Token token = new Token(appointment);
        return tokenRepository.save(token);
    }

    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

    public String generateConfirmationUrl(Token token) {
        String baseUrl = "http://localhost:8080";
        return baseUrl + "/confirm-email?token=" + token.getToken();
    }

    public boolean confirmEmail(String token) {
        Token confirmationToken = findByToken(token);
        if (confirmationToken != null && !confirmationToken.isExpired()) {
            appointmentService.updateConfirmed(true, confirmationToken.getAppointment().getId());
            deleteToken(confirmationToken);
            return true;
        }
        return false;
    }
}

