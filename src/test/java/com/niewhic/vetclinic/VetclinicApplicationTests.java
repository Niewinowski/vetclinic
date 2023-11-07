package com.niewhic.vetclinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VetclinicApplicationTests {

	@Test
	void contextLoads() {
	}

	// aplikacja vetclinic ma w modelu zawierac nastepujace tzw Encje:
	// doctor (id, name, lastName, NIP, rate, specialty, animalSpecialty)
	// patient(id, name, ownerName, ownerLastName, dateOfBirth, ownerEmail, species, breed)
	// appointment(id, doctor, patient, localDateTime, notes, prescription)





}
