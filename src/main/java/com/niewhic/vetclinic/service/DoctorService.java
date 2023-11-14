package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.model.doctor.Doctor;
import com.niewhic.vetclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Doctor findById(long id) {
        return doctorRepository.findById(id).orElseThrow();
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

//    private Map<Long, Doctor> doctorMap = new ConcurrentHashMap<>();
//
//    public Collection<Doctor> getAll() {
//        return doctorMap.values();
//    }
//
//    public Doctor getById(long id) {
//        if (!doctorMap.containsKey(id)) {
//            throw new IllegalArgumentException("There is no doctor with ID " + id);
//        }
//        return doctorMap.get(id);
//    }
//
//    public void add(Doctor doctor) {
//        if (doctorMap.containsKey(doctor.getId())) {
//            throw new IllegalArgumentException("Doctor with ID" + doctor.getId() + " already exists.");
//        }
//        doctorMap.put(doctor.getId(), doctor);
//    }
//
//    public void delete(long id) {
//        doctorMap.remove(id);
//    }
//
//    public void update(long id, Doctor updatedDoctor) {
//        if (doctorMap.containsKey(id)) {
//            doctorMap.put(id, updatedDoctor);
//        }
//    }
//
//    public void edit(long id, Doctor updatedDoctor) {
//        doctorMap.computeIfPresent(id, (key, existingDoctor) -> {
//            Optional.ofNullable(updatedDoctor.getName()).ifPresent(existingDoctor::setName);
//            Optional.ofNullable(updatedDoctor.getLastName()).ifPresent(existingDoctor::setLastName);
//            Optional.ofNullable(updatedDoctor.getNIP()).ifPresent(existingDoctor::setNIP);
//            Optional.ofNullable(updatedDoctor.getRate()).ifPresent(existingDoctor::setRate);
//            Optional.ofNullable(updatedDoctor.getSpecialty()).ifPresent(existingDoctor::setSpecialty);
//            Optional.ofNullable(updatedDoctor.getAnimalSpecialty()).ifPresent(existingDoctor::setAnimalSpecialty);
//            return existingDoctor;
//        });
//    }
}
