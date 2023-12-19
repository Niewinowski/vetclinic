package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.PatientNotFoundException;
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
    //Bean w Springu to podstawowy element frameworka Spring, reprezentujący obiekt zarządzany przez kontener Springa. Spring Bean to zwykły obiekt Java, który jest zainicjalizowany, zmontowany, zarządzany i dostarczany przez Spring IoC (Inversion of Control) Container. Są one tworzone przez kontener Springa na podstawie definicji zawartej w konfiguracji aplikacji, która może być zrealizowana za pomocą XML, adnotacji lub kodu Java.
    //
    //Główne cechy Beanów Springowych:
    //
    //Zarządzanie cyklem życia: Spring zarządza całym cyklem życia beana, od jego utworzenia do zniszczenia.
    //
    //Wstrzykiwanie zależności (Dependency Injection): Beany mogą być konfigurowane do automatycznego wstrzykiwania zależności przez Springa. Oznacza to, że obiekty, które bean potrzebuje do działania, mogą być automatycznie dostarczane przez Springa.
    //
    //Zakres (Scope): Bean może mieć różne zakresy, takie jak singleton (domyślnie), prototype, request, session i global session. Zakres określa, jak często tworzony jest nowy egzemplarz beana.
    //
    //Zarządzanie zasobami: Spring zapewnia wygodne zarządzanie zasobami, takimi jak połączenia z bazą danych, sesje Hibernate itp.
    //
    //Integracja z różnymi technologiami: Beany mogą być zintegrowane z różnymi technologiami i frameworkami, takimi jak JPA, Hibernate, JMS, JDBC itp.
    //
    //Transakcyjność: Beany mogą być konfigurowane do obsługi transakcji.
    //
    //Tworzenie beana w Springu może być zrealizowane na kilka sposobów:
    //
    //Za pomocą adnotacji: Najczęściej używane adnotacje do tworzenia beana to @Component, @Service, @Repository, @Controller itp., które są stereotypami dla różnych warstw aplikacji.
    //
    //Za pomocą konfiguracji XML: Definiowanie beana w plikach konfiguracyjnych XML.
    //
    //Za pomocą konfiguracji opartej na Javie: Definiowanie beana w klasach konfiguracyjnych za pomocą adnotacji @Bean.
    //
    //Bean w Springu to więc po prostu obiekt, który jest zarządzany przez Spring IoC Container, co daje wiele korzyści w zakresie łatwości konfiguracji, zarządzania zależnościami i integracji z innymi elementami aplikacji.

    private final PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(String.format("Patient with id %s not found", id)));
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
                    patientToEdit.setOwnerName(patient.getOwnerName());
                    patientToEdit.setOwnerLastName(patient.getOwnerLastName());
                    patientToEdit.setDateOfBirth(patient.getDateOfBirth());
                    patientToEdit.setOwnerEmail(patient.getOwnerEmail());
                    patientToEdit.setSpecies(patient.getSpecies());
                    patientToEdit.setBreed(patient.getBreed());
                    return patientToEdit;
                }).orElseThrow(() -> new PatientNotFoundException(String.format("Patient with id %s not found", id)));
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
                }).orElseThrow(() -> new PatientNotFoundException(String.format("Patient with id %s not found", id)));
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
