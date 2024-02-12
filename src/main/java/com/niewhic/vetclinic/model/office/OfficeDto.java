package com.niewhic.vetclinic.model.office;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDto {
    private long id;
    private String type;
}
