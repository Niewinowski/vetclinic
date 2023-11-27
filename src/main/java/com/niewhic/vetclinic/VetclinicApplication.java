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

}
