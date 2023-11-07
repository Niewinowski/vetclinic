package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.doctor.Doctor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DoctorRepository {

    private Map<Long, Doctor> doctorMap;

    // metody na dodanie, znajdowanie, edycje itp jak bylo w testcontroller
}
