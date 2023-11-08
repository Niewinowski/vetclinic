package com.niewhic.vetclinic.model.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Patient {
    private long id;
    private String name;
    private String ownerName;
    private String ownerLastName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
    private String ownerEmail;
    private String species;
    private String breed;

    public Patient(long id, String name, String ownerName, String ownerLastName, LocalDateTime dateOfBirth, String ownerEmail, String species, String breed) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.ownerLastName = ownerLastName;
        this.dateOfBirth = dateOfBirth;
        this.ownerEmail = ownerEmail;
        this.species = species;
        this.breed = breed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
