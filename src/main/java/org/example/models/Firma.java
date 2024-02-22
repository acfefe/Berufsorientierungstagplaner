package org.example.models;

import java.util.List;

public record Firma(String name, int firmenID, int anzahlWuensche, List<Zeitslot> verfuegbareZeiten, int maximaleAnzahlSchueler,
                    List<Zeitslot> gebuchteZeitslots) {

}
