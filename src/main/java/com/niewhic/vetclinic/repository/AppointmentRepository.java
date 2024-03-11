package com.niewhic.vetclinic.repository;


import com.niewhic.vetclinic.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
     List<Appointment> findByDoctorId(long Id);
     List<Appointment> findByPatientId(long Id);
     List<Appointment> findByOfficeId(long Id);
     @Modifying
     @Transactional
     @Query("UPDATE Appointment SET confirmed = :confirmed WHERE id = :appointmentId")
     void updateConfirmed(@Param("confirmed") boolean confirmed, @Param("appointmentId") long appointmentId);

}
