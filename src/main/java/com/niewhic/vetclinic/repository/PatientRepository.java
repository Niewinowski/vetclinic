package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
