package com.niewhic.vetclinic.controller;

import org.springframework.web.bind.annotation.*;

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
    String tekst = "";
    @GetMapping
    public String zwrocTekst() {
        return tekst;
    }

    @PostMapping
    public void zmienTekst(@RequestBody String tekst) {
        this.tekst = tekst;
    }

    // @DeleteMapping - do usuwania
    // @PutMapping - do zmiany calosci obiektu
    // @PatchMapping - do zmiany czesciowej obiektu
}
