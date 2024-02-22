package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Schueler {
    List<Zeitslot> belegungen;
    private String klasse;
    private String vorname;
    private String nachname;
    private int[] wahlen;
}
