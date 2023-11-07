package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    // mappingi analogicznie jak w testcontrollerze
}
