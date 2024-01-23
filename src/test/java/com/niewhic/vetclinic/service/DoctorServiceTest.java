//package com.niewhic.vetclinic.service;
//
//import com.niewhic.vetclinic.model.doctor.Doctor;
//import com.niewhic.vetclinic.repository.DoctorRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DoctorServiceTest {
//
//    @Mock
//    private DoctorRepository doctorRepository;
//
//    @InjectMocks
//    private DoctorService doctorService;
//
//    @BeforeEach
//    void setUp() {
//        doctorService = new DoctorService(doctorRepository);
//    }
//
//    @Test
//    void addDoctor() {
//        Doctor doctor = Doctor.builder()
//                .id(1L)
//                .name("John")
//                .lastName("Smith")
//                .rate(5)
//                .specialty("Cardiology")
//                .animalSpecialty("Dogs")
//                .build();
//        when(doctorRepository.save(doctor)).thenReturn(doctor);
//        Doctor savedDoctor = doctorService.save(doctor);
//        assertAll(
//                () -> assertNotNull(savedDoctor),
//                () -> assertEquals(doctor.getId(), savedDoctor.getId()),
//                () -> assertEquals(doctor.getName(), savedDoctor.getName()),
//                () -> assertEquals(doctor.getSpecialty(), savedDoctor.getSpecialty())
//        );
//        verify(doctorRepository, times(1)).save(doctor);
//    }
//
//    // Twoje wątpliwości co do użycia metody any() w teście jednostkowym dla klasy Doctor są uzasadnione.
//    // Metoda any() z Mockito jest używana do tworzenia matcherów, które pasują do dowolnego argumentu pasującego do danego typu.
//    // W tym przypadku any(Doctor.class) oznacza, że Mockito spodziewa się, że metoda save zostanie wywołana z dowolnym obiektem typu Doctor.
//    //
//    //Oto kilka punktów, które warto rozważyć:
//    //
//    //Precyzja testu: Użycie any() czyni test mniej precyzyjnym. W idealnym przypadku chcesz dokładnie wiedzieć, z jakim
//    // obiektem została wywołana metoda save. Jeśli Twój cel to sprawdzenie, czy metoda save została wywołana
//    // z dokładnie tym samym obiektem doctor, który został utworzony w teście, lepiej użyć eq(doctor) zamiast any(Doctor.class).
//    //
//    //Intencja testu: Jeśli jednak intencją testu jest po prostu sprawdzenie, czy metoda save została wywołana z jakimkolwiek
//    // obiektem typu Doctor, bez względu na jego stan, wtedy użycie any() jest właściwe.
//    //
//    //Potencjalne błędy: Użycie any() może czasami maskować błędy, gdyż nie sprawdza konkretnych wartości obiektów.
//    // Jeśli logika w save zależy od konkretnych wartości w obiekcie Doctor, testy mogą nie ujawnić pewnych błędów.
//    //
//    //Alternatywy: Rozważ użycie ArgumentCaptor do przechwycenia dokładnego obiektu przekazanego do metody save.
//    // Dzięki temu możesz dokładnie zweryfikować, czy stan przekazanego obiektu jest taki, jakiego się spodziewasz.
//    //
//    //Czytelność: Zastanów się, czy użycie any() wpływa na czytelność i zrozumienie testu przez innych programistów.
//    // Czasami bardziej szczegółowe dopasowanie może uczynić intencje testu jaśniejszymi.
//    //
//    //Podsumowując, jeśli zależy Ci na dokładnym dopasowaniu obiektu przekazanego do metody save, warto zastanowić się nad użyciem bardziej specyficznego matchera niż any().
//
//    // Masz rację, pytając o różnicę między użyciem eq(doctor) a po prostu doctor w kontekście Mockito. Kluczowa różnica leży w sposób działania Mockito i jego matcherów.
//    //
//    //Użycie doctor bezpośrednio: Kiedy podajesz obiekt bezpośrednio w metodzie when(...).thenReturn(...),
//    // Mockito oczekuje, że dokładnie ten sam obiekt będzie użyty w wywołaniu metody mockowanego obiektu.
//    // Oznacza to, że Mockito porównuje referencje, a nie stan obiektu.
//    // Jeśli metoda save zostanie wywołana z innym obiektem Doctor, nawet jeśli jest on identyczny pod względem wartości pól,
//    // Mockito nie uzna tego za spełnienie warunku w when().
//    //
//    //Użycie eq(doctor): Matcher eq() z Mockito jest używany, kiedy chcesz dopasować argumenty metod na podstawie ich wartości,
//    // a nie referencji. W tym przypadku jednak, ponieważ eq() jest matcherem, musisz być konsekwentny i używać matcherów
//    // dla wszystkich argumentów metody w when(). Jeśli użyjesz eq() dla jednego argumentu, a zwykłego obiektu dla innego,
//    // Mockito wyrzuci wyjątek, ponieważ wymaga stosowania matcherów spójnie.
//    //
//    //W Twoim przypadku, jeśli chcesz, aby Mockito sprawdziło, czy metoda save została wywołana dokładnie z obiektem doctor,
//    // który został utworzony w teście, najlepiej jest użyć bezpośrednio doctor w when(doctorRepository.save(doctor)).thenReturn(doctor).
//    // To zapewnia, że mockowany jest konkretny wywołanie z określonym obiektem. Matcher eq() jest przydatny,
//    // gdy stan obiektu jest ważniejszy niż jego konkretna instancja, lub gdy używasz innych matcherów w tej samej instrukcji when().
//    @Test
//    void findDoctorById() {
//        long doctorId = 1L;
//        Doctor doctor = Doctor.builder()
//                .id(doctorId)
//                .name("John")
//                .specialty("Cardiology")
//                .build();
//        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
//        Doctor foundDoctor = doctorService.findById(doctorId);
//        assertAll(
//                () -> assertNotNull(foundDoctor),
//                () -> assertEquals(doctorId, foundDoctor.getId()),
//                () -> assertEquals("John", foundDoctor.getName())
//        );
//        verify(doctorRepository, times(1)).findById(doctorId);
//    }
//
//    @Test
//    void deleteDoctor() {
//        long doctorId = 1L;
//        doNothing().when(doctorRepository).deleteById(doctorId);
//        doctorService.delete(doctorId);
//        verify(doctorRepository, times(1)).deleteById(doctorId);
//    }
//
//
//    @Test
//    void findAllDoctors() {
//        Doctor doctor1 = Doctor.builder()
//                .id(1L)
//                .name("John")
//                .lastName("Smith")
//                .rate(5)
//                .specialty("Cardiology")
//                .animalSpecialty("Dogs")
//                .build();
//
//        Doctor doctor2 = Doctor.builder()
//                .id(2L)
//                .name("Jane")
//                .lastName("Doe")
//                .rate(4)
//                .specialty("Neurology")
//                .animalSpecialty("Cats")
//                .build();
//
//
//        when(doctorRepository.findAll()).thenReturn(List.of(doctor1, doctor2));
//
//        List<Doctor> doctorList = doctorService.findAll();
//
//        assertNotNull(doctorList);
//        assertEquals(2, doctorList.size());
//    }
//
//    @Test
//    void givenNonExistingDoctorId_whenFindById_thenThrowsException() {
//        long id = 100L;
//        when(doctorRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(NoSuchElementException.class, () -> doctorService.findById(id));
//    }
//
//
//    @Test
//    void findAllDoctors_whenNoDoctorsExist() {
//        when(doctorRepository.findAll()).thenReturn(List.of());
//
//        List<Doctor> doctorList = doctorService.findAll();
//
//        assertTrue(doctorList.isEmpty());
//    }
//    @Test
//    void deleteNonExistingDoctor() {
//        long nonExistingDoctorId = 999L;
//        doThrow(new NoSuchElementException()).when(doctorRepository).deleteById(nonExistingDoctorId);
//
//        assertThrows(NoSuchElementException.class, () -> doctorService.delete(nonExistingDoctorId));
//
//        verify(doctorRepository, times(1)).deleteById(nonExistingDoctorId);
//    }
//    @Test
//    void addDoctor_withMissingFields() {
//        Doctor incompleteDoctor = Doctor.builder()
//                .id(3L)
//                .name("Alice")
//                .build();
//
//        when(doctorRepository.save(incompleteDoctor)).thenReturn(incompleteDoctor);
//
//        Doctor savedDoctor = doctorService.save(incompleteDoctor);
//
//        assertNotNull(savedDoctor);
//        assertEquals(incompleteDoctor.getName(), savedDoctor.getName());
//        assertNull(savedDoctor.getLastName());
//
//    }
//
//    @Test
//    void updateDoctorPartially() {
//        long doctorId = 1L;
//        Doctor existingDoctor = Doctor.builder()
//                .id(doctorId)
//                .name("John")
//                .lastName("Doe")
//                .specialty("Cardiology")
//                .rate(5)
//                .animalSpecialty("Dogs")
//                .build();
//
//        Doctor updatedDoctor = Doctor.builder()
//                .id(doctorId)
//                .name("Jane")
//                .lastName(null)
//                .specialty("Neurology")
//                .build();
//
//        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
//
//        Doctor result = doctorService.editPartially(doctorId, updatedDoctor);
//
//        assertAll(
//                () -> assertEquals("Jane", result.getName()),
//                () -> assertEquals("Doe", result.getLastName()),
//                () -> assertEquals("Neurology", result.getSpecialty()),
//                () -> assertEquals(5, result.getRate()),
//                () -> assertEquals("Dogs", result.getAnimalSpecialty())
//        );
//
//        verify(doctorRepository, times(1)).findById(doctorId);
//    }
//
//
//}