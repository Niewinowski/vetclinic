package com.niewhic.vetclinic.model.appointment.command;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateAppointmentCommand {
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Date and time cannot be null")
    @PastOrPresent(message = "Date and time must be in the past or future")
    private LocalDateTime dateTime;

    @NotBlank(message = "Notes cannot be blank")
    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @Size(max = 500, message = "Prescription cannot exceed 500 characters")
    private String prescription;
}