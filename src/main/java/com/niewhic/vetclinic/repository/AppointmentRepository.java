package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AppointmentRepository {
    private Map<Long, Appointment> appointments = new HashMap<>();
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentRepository(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Collection<Appointment> getAll() {
        return appointments.values();
    }

    public Appointment getById(long id) {
        return appointments.get(id);
    }

    public void add(Appointment appointment) {
        Doctor doctor = doctorRepository.getById(appointment.getDoctorId());
        Patient patient = patientRepository.getById(appointment.getPatientId());
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found with ID: " + appointment.getDoctorId());
        } else if (patient == null) {
            throw new IllegalArgumentException("Patient not found with ID: " + appointment.getPatientId());
        }
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointments.put(appointment.getId(), appointment);
    }

    public void delete(long id) {
        appointments.remove(id);
    }

    public void update(long id, Appointment updatedAppointment) {
        appointments.replace(id, updatedAppointment);
    }
}
