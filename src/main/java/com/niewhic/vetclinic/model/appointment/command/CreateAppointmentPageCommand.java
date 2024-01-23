package com.niewhic.vetclinic.model.appointment.command;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAppointmentPageCommand {

    private int pageNumber = 0;
    private int pageSize = 2;
    @Pattern(regexp = "ASC|DESC",
            message = "INVALID_SORT_DIRECTION_VALUE")
    private String sortDirection = "ASC";
    @Pattern(regexp = "id|doctorId|patientId|dateTime|notes|prescription",
            message = "INVALID_SORT_BY_VALUE")
    private String sortBy = "id";
}