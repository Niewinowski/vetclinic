package com.niewhic.vetclinic.converter;

import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.model.office.OfficeDto;
import org.springframework.stereotype.Component;
@Component
public class OfficeToOfficeDtoConverter {

    public OfficeDto convert(Office office) {
        return OfficeDto.builder()
                .id(office.getId())
                .type(office.getType())
                .build();
    }
}