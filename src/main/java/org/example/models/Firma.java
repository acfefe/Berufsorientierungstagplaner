package org.example.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Firma {

    private int firmenID;
    private String name;
    private int anzahlWuensche;
    private List<Zeitslot> verfuegbareZeiten;
    private int maximaleAnzahlSchueler;
    private ArrayList<String> gebuchteZeitslots;
    private int anzahlVeranstaltung;
}
