package org.example.models;

import lombok.Data;

@Data
public class Schueler {
    private String klasse;
    private String vorname;
    private String nachname;
    private int[] wahlen;

}
