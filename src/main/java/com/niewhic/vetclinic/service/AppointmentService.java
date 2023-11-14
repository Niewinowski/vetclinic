package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.converter.CreateAppointmentCommandToAppointmentConverter;
import com.niewhic.vetclinic.model.appointment.Appointment;
import com.niewhic.vetclinic.model.appointment.CreateAppointmentCommand;
import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.model.patient.Patient;
import com.niewhic.vetclinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CreateAppointmentCommandToAppointmentConverter converter;

    public Appointment save(CreateAppointmentCommand command) {
        return appointmentRepository.save(converter.convert(command));
    }

//    public Collection<Appointment> getAll() {
//        return appointments.values();
//    }
//
//    public Appointment getById(long id) {
//        return appointments.get(id);
//    }

//    public void add(Appointment appointment) {
//        List<Long> doctorsId = doctorService.getAll().stream()
//                .map(Doctor::getId)
//                .toList();
//        List<Long> patientsId = patientService.findAll().stream()
//                .map(Patient::getId)
//                .toList();
//        if (!doctorsId.contains(appointment.getDoctor().getId())) {
//            throw new IllegalArgumentException("Doctor not found");
//        } else if (!patientsId.contains(appointment.getPatient().getId())) {
//            throw new IllegalArgumentException("Patient not found");
//        }
//        appointments.put(appointment.getId(), appointment);
//    }

//    public void delete(long id) {
//        appointments.remove(id);
//    }
//
//    public void update(long id, Appointment updatedAppointment) {
//        appointments.replace(id, updatedAppointment);
//    }
}
