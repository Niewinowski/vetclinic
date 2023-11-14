package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
