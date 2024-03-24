package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firma {

    private int firmenID;
    private String name;
    private int anzahlWuensche;
    private List<Veranstaltung> veranstaltungen;
    private int maximaleAnzahlSchueler;

    // private List<Zeitslot> verfuegbareZeiten;
    // private ArrayList<String> gebuchteZeitslots;
    // private int anzahlVeranstaltung;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Veranstaltung {
        private String zeitslot;
        private Raum raum;
    }
}
