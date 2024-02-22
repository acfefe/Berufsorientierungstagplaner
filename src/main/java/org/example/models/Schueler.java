package org.example.models;

import java.util.List;

public record Schueler(String klasse, String vorname, String nachname, int[] wahlen, List<Zeitslot> belegungen) {

}
