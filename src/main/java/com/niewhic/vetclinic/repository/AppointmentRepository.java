package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AppointmentRepository {
    private final Map<Long, Appointment> appointments = new HashMap<>();
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
        List<Long> doctorsId = doctorRepository.getAll().stream()
                .map(Doctor::getId)
                .toList();
        List<Long> patientsId = patientRepository.getAll().stream()
                .map(Patient::getId)
                .toList();
        if (!doctorsId.contains(appointment.getDoctor().getId())) {
            throw new IllegalArgumentException("Doctor not found");
        } else if (!patientsId.contains(appointment.getPatient().getId())) {
            throw new IllegalArgumentException("Patient not found");
        }
        appointments.put(appointment.getId(), appointment);
    }

    public void delete(long id) {
        appointments.remove(id);
    }

    public void update(long id, Appointment updatedAppointment) {
        appointments.replace(id, updatedAppointment);
    }
}
