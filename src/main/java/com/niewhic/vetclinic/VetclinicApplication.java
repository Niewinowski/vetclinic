package com.niewhic.vetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VetclinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(VetclinicApplication.class, args);
	}
	// aplikacja vetclinic ma w modelu zawierac nastepujace tzw Encje:
	// doctor (id, name, lastName, NIP, rate, specialty, animalSpecialty)
	// patient(id, name, ownerName, ownerLastName, dateOfBirth, ownerEmail, species, breed)
	// appointment(id, doctor, patient, localDateTime, notes, prescription)

	// dokonczyc przerobienie na service/repo patient, doctor, appointment
	// jak przyjmujemy cos jsonem przez RequestBody niech to bedzie przyjete jako command (czyli stworzyc nowe commandy dla create i edit)
	// wszedzie gdzie cos zwracamy z kontrolera, zwrocmy jako responseEntity

	// TODO 20.11.2023:
	// niech endpointy z saved zwracaja w ReponseEntity to, co jest zapisane
	// stworzyc commandy do tworzenia i edycji wszystkiego (czyli tez do doctora, patienta i edit do appointment)
	// stworzyc klasy Dto (data transfer object) dla modeli
	// DTO, czyli Data Transfer Object, w kontekście Javy, to wzorzec projektowy używany do transferu danych
	// między warstwami i modułami w aplikacji. Jest to prosta klasa Java, która zawiera jedynie pola danych
	// i metody dostępu do tych danych (gettery i settery). DTO nie zawiera żadnej logiki biznesowej
	// ani zachowań poza przechowywaniem i przekazywaniem danych.
	// DoctorDto, PatientDto, AppointmentDto
	// przy AppointmentDto bedzie ptorzebny konwerter AppointmentToAppointmentDtoConverter
	// zaimplementowac modelmapper i uzywac jego konwerterow dla appointment
	// po skonczeniu powyzszego napiszemy testy jednostkowe dla konwerterow i warstwy serwisowej
	// TODO
	// wszedzie uzywac DTO i konwertera tak gdzie go nie ma
	//
//	https://www.baeldung.com/java-command-pattern

	// TODO na nastepnych zajeciach: GlobalExceptionHandler i testy controllerow

	// TODO naprawic editAppointment, trzeba dodac konwerter dla EditAppointmentCommand
	// TODO dodac konkretne wyjatki pod model: DoctorNotFoundException, PatientNotFoundException, AppointmentNotFoundException
	// TODO i obsluzyc je w globalExceptionHandler

	// TODO 2023-12-19 | Update, Add, FindById - Janek, Delete, FindAllEditPartially - Wojciech

	// TODO 2024-01-09 | zaimplementowac softDelete w patient i w appointment
	// naprawic dane testowe - stworzyc nowe changesety do modyfikacji istniejacych tabel
	// zmodyfikowac pliki csv z ktorych ladujemy dane
	// changesets naming convention modify-table-patient.xml, modify-table-doctor.xml, modify-table-appointment.xml

	// TODO 2024-01-16 |
	// dokonczyc paginacje i sortowanie w pozostalych klasach, naprawic sortDirection :)
	// TESTY TESTY TESTY
	// poprawic testy
	// dodac walidacje danych w commandach - wszystkie/wiekszosc (do waszego uznania) pol np nie powinno byc puste
	// adnotacje @NotBlank/@NotEmpty/@NotNull - poczytajcie
	// @Validated/@Valid w controllerze - tez poczytajcie

	//	1. Adnotacje @NotBlank, @NotEmpty, @NotNull:
	//
	//		 @NotNull:  Adnotacja ta jest stosowana do pól w klasach Java i wskazuje, że dane pole nie może być null.
	//					Jest to podstawowa walidacja sprawdzająca, czy wartość została w ogóle podana.

	//		 @NotEmpty: Ta adnotacja jest używana głównie dla kolekcji, tablic i ciągów znaków (String).
	//					Wskazuje ona, że pole nie może być null i nie może być puste. W przypadku stringów oznacza to,
	//					że string musi zawierać co najmniej jeden znak.

	//		 @NotBlank: Jest podobna do @NotEmpty, ale dodatkowo sprawdza, czy ciąg znaków nie składa się wyłącznie
	//					z białych znaków (spacje, tabulacje itp.). Jest to użyteczne w przypadku walidacji tekstów,
	//					gdzie nie chcemy akceptować ciągów składających się tylko z białych znaków.

	//	2. @Valid i @Validated w kontrolerze:
	//
	//		@Valid: 	Ta adnotacja jest stosowana do argumentów metod w kontrolerze Spring MVC lub REST,
	//					aby włączyć walidację dla przekazywanych obiektów (np. modeli formularzy, DTO).
	//					Gdy używamy @Valid przed argumentem metody, Spring automatycznie sprawdza,
	//					czy obiekt spełnia kryteria walidacyjne zdefiniowane w jego klasie
	//					(np. za pomocą adnotacji @NotBlank, @NotEmpty itd.). Jeśli walidacja nie powiedzie się,
	//					metoda nie zostanie wykonana, a zamiast tego zostanie zgłoszony wyjątek.

	//		@Validated: Jest to bardziej zaawansowana wersja @Valid, która pochodzi z pakietu Springa
	//					i oferuje dodatkową elastyczność, np. grupy walidacyjne.
	//					Pozwala to na bardziej szczegółową kontrolę nad procesem walidacji.

	// 					Grupy walidacyjne pozwalają na określenie, kiedy konkretne warunki walidacji mają być stosowane.
	// 					Dzięki temu możesz mieć różne zasady walidacji dla różnych operacji w tej samej klasie.
	// 					Na przykład, możesz mieć jedną grupę walidacyjną dla procesu tworzenia obiektu (Create)
	// 					i inną dla jego aktualizacji (Update).


	// TODO 2024-01-22
	// Zadanie domowe - dodać do Appointmentu Encję Gabinet i umieszczać wizyty w Gabinecie. Walidacja czy jest dostępny
	// gabinet w czasie wizyty. Danego dnia w przedzialach czasowych jaki Doctor przyjmuje w jakim gabinecie. Do przemyślenia
	// rozwiązanie.

	// Zaimplementować @Validated - przykład.

	// TODO 30.01.2024 - wysylka maili
	// stworzenie appointmentu powinno wyslac do pacjenta maila z prosba o potwierdzenie.
	// dajmy w appointmencie flage, np confirmed, na poczatku jest false, jak potwierdzi to jest true
	// w tresci maila powinien byc link do potiwerdzenia konkretnej wizyty
	// testy testy testy :)
	// bonus: zaimplementowac slownik, tzn mozliwosc wysylki maila po polsku lub po angielsku, wypelnianego zmiennymi typu id wizyty, dane pacjenta itp


	// TODO dokonczyc powyzsze
	// TODO postawic liquibase dla glownej aplikacji, a nie tylko dla testow. To w 90% bedzie copy-paste

	// TODO 19.02.2024
	// AppointmentController - wyniesc logike biznesowa dotyczaca wysylki maila do service
	// poczytac o Spring Basic Security

	//	### Kluczowe cechy Spring Basic Security:
	//
	//	1. Uwierzytelnianie: 	Polega na potwierdzeniu tożsamości użytkownika. W przypadku Basic Security, Spring używa podstawowego uwierzytelniania HTTP,
	//							gdzie użytkownik musi dostarczyć nazwę użytkownika i hasło, które są przesyłane w nagłówku żądania HTTP w postaci zakodowanej w Base64.
	//
	//	2. Autoryzacja: 		Określa, co uwierzytelniony użytkownik może robić w aplikacji. Spring Security umożliwia określenie,
	//							które zasoby są dostępne dla różnych użytkowników lub ról.
	//
	//	3. Ochrona przed atakami CSRF: Spring Security oferuje ochronę przed atakami CSRF, generując token CSRF, który musi być dołączony do każdego żądania modyfikującego stan,
	//									co zapobiega nieautoryzowanym działaniom w imieniu uwierzytelnionego użytkownika.
	//
	//	4. Sesje: 				Spring Security zarządza sesjami użytkowników, oferując mechanizmy ich tworzenia, utrzymania oraz zakończenia po uwierzytelnieniu.
	//
	//	### Dlaczego warto używać Spring Basic Security?
	//
	//	+ Szybka konfiguracja: 		Dla wielu aplikacji, podstawowa konfiguracja bezpieczeństwa jest wystarczająca do ochrony przed powszechnymi zagrożeniami.
	//	+ Elastyczność: 			Chociaż jest to "podstawowy" poziom bezpieczeństwa, Spring Security umożliwia jego dostosowanie i rozszerzenie w miarę rosnących wymagań aplikacji.
	//	+ Wsparcie społeczności: 	Spring Security jest szeroko stosowany i wspierany przez dużą społeczność, co ułatwia znalezienie rozwiązań potencjalnych problemów.
	//
	//	### Jak zacząć? ###
	//
	//	Aby skonfigurować Spring Basic Security, należy dodać zależność Spring Security do pliku konfiguracyjnego projektu (np. pom.xml dla Mavena lub build.gradle dla Gradle).
	//	Następnie, konfiguracja bezpieczeństwa może być realizowana za pomocą klas konfiguracyjnych Java (z adnotacjami @Configuration i @EnableWebSecurity) lub za pomocą konfiguracji XML.
	//
	//	Podstawowa konfiguracja wymaga zdefiniowania co najmniej jednego użytkownika (z nazwą użytkownika i hasłem) oraz określenia,
	//	które ścieżki w aplikacji są chronione i które wymagają uwierzytelnienia.

	// TODO 26.02.2024 - domowe - zaimplementowac Swagger
	// macie springboota 3, wiec swaggera tez wezcie w wersji 3 :)
	// wzor jak to ma wygladac:
	// popatrzcie, oprocz uruchomienia swaggera, jak dodawac adnotacje do controllerow - ednpointow- aby je odpowiednio opisac (co przyjmuja, co zwracaja)
	//	https://petstore.swagger.io/
	// Patient & Appointment - Jan, Office & Doctor - Wojtek


	// TODO 04.03.2024 - rozbudowa security
	// Chcemy trzymac userow i ich role w bazie i z tych trzymanych userow korzystac przy logowaniu i/lub ustawianiu kto co moze

	// UserRole to USER oraz ADMIN
	// rola USER ma pozwolenie UserPermission.READ
	// rola ADMIN ma pozwolenie UserPermission.READ, UserPermission.WRITE, UserPermission.DELETE

	// UserService musi implementowac UserDetailsService:
	//	import org.springframework.security.core.userdetails.UserDetailsService;


}
