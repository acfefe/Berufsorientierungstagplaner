package org.example.models;

import lombok.Data;

@Data
public class Schueler {
    String klasse;

    String name;

    String vorname;

    int[] wahlen = new int[6];

    int[] belegungen = new int[5];
}
