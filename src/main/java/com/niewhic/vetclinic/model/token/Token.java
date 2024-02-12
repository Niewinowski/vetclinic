package com.niewhic.vetclinic.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niewhic.vetclinic.model.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime expiryDate;

    public Token(Appointment appointment) {
        this.token = UUID.randomUUID().toString();
        this.appointment = appointment;
        this.expiryDate = calculateExpiryDate();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    private LocalDateTime calculateExpiryDate() {
        int expirationHours = 24;
        return LocalDateTime.now().plusHours(expirationHours);
    }
}
