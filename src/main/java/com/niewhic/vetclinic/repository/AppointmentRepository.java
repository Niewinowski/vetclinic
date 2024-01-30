package com.niewhic.vetclinic.repository;


import com.niewhic.vetclinic.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
     List<Appointment> findByDoctorId(long Id);
     List<Appointment> findByPatientId(long Id);
     List<Appointment> findByOfficeId(long Id);
}
