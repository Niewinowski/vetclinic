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


}
