package com.niewhic.vetclinic.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Osoba {

    private long id;
    private String imie;
    private String nazwisko;
    private BigDecimal pensja;

}
