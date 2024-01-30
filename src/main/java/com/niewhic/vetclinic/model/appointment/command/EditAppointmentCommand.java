package com.niewhic.vetclinic.model.appointment.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EditAppointmentCommand {
    private Long doctorId;
    private LocalDateTime dateTime;
    private String notes;
    private String prescription;
}
