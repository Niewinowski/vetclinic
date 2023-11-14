package com.niewhic.vetclinic.repository;


import com.niewhic.vetclinic.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
