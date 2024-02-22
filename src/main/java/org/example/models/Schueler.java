package org.example.models;

import lombok.Data;

@Data
public class Schueler {

    private String vorname;
    private String nachname;
    private String klasse;
    private int[] wahlen;

}
