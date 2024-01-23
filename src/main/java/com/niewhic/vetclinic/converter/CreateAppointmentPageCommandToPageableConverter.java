package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.appointment.command.CreateAppointmentPageCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.valueOf;
import static org.springframework.data.domain.Sort.by;

@Service
public class CreateAppointmentPageCommandToPageableConverter implements Converter<CreateAppointmentPageCommand, Pageable> {

    @Override
    public Pageable convert(MappingContext<CreateAppointmentPageCommand, Pageable> mappingContext) {
        CreateAppointmentPageCommand pageCommand = mappingContext.getSource();
        return of(
                pageCommand.getPageNumber(),
                pageCommand.getPageSize(),
                by(valueOf(pageCommand.getSortDirection().toUpperCase()),
                        pageCommand.getSortBy())
        );
    }
}