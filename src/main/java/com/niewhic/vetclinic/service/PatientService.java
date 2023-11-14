package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.repository.PatientRepository;
import com.niewhic.vetclinic.model.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    // todo co to jest Bean springowy

    private final PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }

    public Patient save(Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    public void delete(long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public Patient edit(long id, Patient patient) {
        return patientRepository.findById(id)
                .map(patientToEdit -> {
                    patientToEdit.setId(id);
                    patientToEdit.setName(patient.getName());
                    patientToEdit.setBreed(patient.getBreed());
                    patientToEdit.setSpecies(patient.getSpecies());
                    patientToEdit.setOwnerEmail(patient.getOwnerEmail());
                    patientToEdit.setOwnerName(patient.getOwnerName());
                    patientToEdit.setOwnerLastName(patient.getOwnerLastName());
                    patientToEdit.setDateOfBirth(patient.getDateOfBirth());
                    return patientToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }

    @Transactional
    public Patient editPartially(long id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(patientToEdit -> {
                    Optional.ofNullable(updatedPatient.getName()).ifPresent(i -> patientToEdit.setName(updatedPatient.getName()));
                    Optional.ofNullable(updatedPatient.getOwnerName()).ifPresent(i -> patientToEdit.setOwnerName(updatedPatient.getOwnerName()));
                    Optional.ofNullable(updatedPatient.getOwnerLastName()).ifPresent(i -> patientToEdit.setOwnerLastName(updatedPatient.getOwnerLastName()));
                    Optional.ofNullable(updatedPatient.getDateOfBirth()).ifPresent(i -> patientToEdit.setDateOfBirth(updatedPatient.getDateOfBirth()));
                    Optional.ofNullable(updatedPatient.getOwnerEmail()).ifPresent(i -> patientToEdit.setOwnerEmail(updatedPatient.getOwnerEmail()));
                    Optional.ofNullable(updatedPatient.getSpecies()).ifPresent(i -> patientToEdit.setSpecies(updatedPatient.getSpecies()));
                    Optional.ofNullable(updatedPatient.getBreed()).ifPresent(i -> patientToEdit.setBreed(updatedPatient.getBreed()));
                    return patientToEdit;
                }).orElseThrow(() -> new NoSuchElementException(String.format("Patient with id %s not found", id)));
    }

    /* Transactional
    Notatka o Adnotacji @Transactional w Javie

Definicja:
Adnotacja @Transactional jest używana w Javie do deklarowania zachowania transakcji na poziomie metody lub klasy.
Jest częścią Spring Framework i używana głównie w kontekście zarządzania transakcjami w aplikacjach baz danych.

Główne Zastosowania:

Zarządzanie Transakcjami: Automatyzuje proces rozpoczynania, zarządzania i kończenia transakcji. Zajmuje się również wycofywaniem transakcji w przypadku wyjątków.
Deklaratywne Zarządzanie Transakcjami: Pozwala na określenie zachowania transakcji bezpośrednio w kodzie za pomocą adnotacji, eliminując potrzebę ręcznego zarządzania transakcjami.
Jak Stosować:

Na Poziomie Klasy: Stosowanie @Transactional na poziomie klasy oznacza, że każda metoda publiczna w tej klasie będzie wykonana w kontekście transakcji.
Na Poziomie Metody: Stosowanie @Transactional bezpośrednio na metodzie pozwala na bardziej szczegółowe kontrolowanie zachowania transakcji.
Atrybuty Adnotacji:

propagation: Określa typ propagacji transakcji.
isolation: Definiuje poziom izolacji transakcji.
timeout: Ustala limit czasu dla transakcji.
readOnly: Wskazuje, czy transakcja jest tylko do odczytu.
rollbackFor: Wymienia wyjątki, które spowodują wycofanie transakcji.
noRollbackFor: Wymienia wyjątki, które nie spowodują wycofania transakcji.
Dobre Praktyki:

Stosuj @Transactional tylko tam, gdzie jest to konieczne.
Unikaj długich transakcji, aby zmniejszyć ryzyko blokowania zasobów.
Bądź świadomy propagacji transakcji, zwłaszcza w złożonych przepływach biznesowych.
Przykład Użycia:

java
Copy code
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SomeService {

    public void someTransactionalMethod() {
        // kod wykonujący operacje w transakcji
    }
}
Uwagi Końcowe:
Adnotacja @Transactional jest potężnym narzędziem upraszczającym zarządzanie transakcjami w aplikacjach baz danych. Należy jednak stosować ją rozważnie, z uwzględnieniem specyfiki działania aplikacji oraz wydajności.

Ta notatka zawiera podstawowe informacje o adnotacji @Transactional, jej zastosowaniu, atrybutach oraz dobrych praktykach związanych z jej stosowaniem.
     */
}
