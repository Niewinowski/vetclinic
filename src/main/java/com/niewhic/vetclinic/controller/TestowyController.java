package com.niewhic.vetclinic.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/testowy")
public class TestowyController {

    //Stworz klase Osoba, niech ma id, imie, nazwisko, pensja
    // w controllerze zrob liste osob
    // stworz metody:
    // 1 pozwalajaca wczytac wszystkie osoby z listy
    // 2 pozwaljaca wczytac osobe po id @GetMapping("/{id}")
    // 3 pozwalajaca wczytac wszystkie osoby o danym imieniu @GetMapping("/imie/{imie}")
    // 4 pozwalajaca wczytac wszystkie osoby o danym nazwisku @GetMapping("/nazwisko/{nazwisko}")
    // 5 pozwalajaca wczytac wszystkie osoby z pensja wyzsza lub rowna tej podanej w parametrze

    // 6 pozwalajaca zapisac osobe do listy
    // 7 pozwalajaca usunac osobe z listy
    // 8 pozwalajaca edytowac osobe w calosci
    // 9 pozwalajaca edytowac osobe czesciowo (optionale to protip)

    // do przyjecia osoby do zapisu wykorzystujemy w parametrze metody  (@RequestBody Osoba osoba)
    // do szukania osoby po id robimy np     @GetMapping("/{id}") i potem w parametrze metody (@PathVariable("id") long id)
    private List<Osoba> ludzie = new ArrayList<>();

    @GetMapping
    public List<Osoba> getAllPeople() {
        return ludzie;
    }

    @GetMapping("/{id}")
    public Osoba getOsobaById(@PathVariable("id") long id) {
        return ludzie.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/imie")
    public List<Osoba> getLudzieByFirstName(@RequestParam String firstName) {
        return ludzie.stream()
                .filter(person -> person.getImie().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    @GetMapping("/nazwisko")
    public List<Osoba> getLudzieByLastName(@RequestParam String lastName) {
        return ludzie.stream()
                .filter(person -> person.getNazwisko().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @GetMapping("/salary")
    public List<Osoba> getLudzieWithSalaryGreaterOrEqual(@RequestParam BigDecimal salary) {
        return ludzie.stream()
                .filter(osoba -> osoba.getPensja() != null && osoba.getPensja().compareTo(salary) <= 0)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addOsoba(@RequestBody Osoba osoba) {
        ludzie.add(osoba);
        System.out.println(ludzie);
    }

    @DeleteMapping("/{id}")
    public void deleteOsoba(@PathVariable("id") long id) {
        ludzie.removeIf(person -> person.getId() == id);
    }

    @PutMapping("/{id}")
    public void updateOsoba(@PathVariable("id") long id, @RequestBody Osoba osoba) {
        ludzie = ludzie.stream()
                .map(p -> p.getId() == id ? osoba : p)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public void editOsoba(@PathVariable("id") long id, @RequestBody Osoba updatedOsoba) {
        ludzie.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .ifPresent(osoba -> {
                    Optional.ofNullable(osoba.getImie()).ifPresent(o -> osoba.setImie(updatedOsoba.getImie()));
//                    if (updatedOsoba.getImie() != null) osoba.setImie(updatedOsoba.getImie());
                    if (updatedOsoba.getNazwisko() != null) osoba.setNazwisko(updatedOsoba.getNazwisko());
                    if (updatedOsoba.getPensja() != null && updatedOsoba.getPensja().compareTo(BigDecimal.ZERO) > 0)
                        osoba.setPensja(updatedOsoba.getPensja());
                });
    }

    String tekst = "";

//    @GetMapping
//    public String zwrocTekst() {
//        return tekst;
//    }

//    @PostMapping
//    public void zmienTekst(@RequestBody String tekst) {
//        this.tekst = tekst;
//    }

    // @DeleteMapping - do usuwania
    // @PutMapping - do zmiany calosci obiektu
    // @PatchMapping - do zmiany czesciowej obiektu
}
