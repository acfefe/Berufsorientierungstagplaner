package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Firma {

    private String name;
    private int firmenID;
    private int anzahlWuensche;
    private List<Zeitslot> verfuegbareZeiten;
    private int maximaleAnzahlSchueler;
    private List<Zeitslot> gebuchteZeitslots;

}
