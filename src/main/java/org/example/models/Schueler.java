package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Schueler {
    String klasse;

    String name;

    String vorname;

    int[] wahlen = new int[6];

    List<Zeitslot> belegungen;
}
