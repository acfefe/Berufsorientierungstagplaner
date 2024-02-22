package org.example.models;

import java.util.List;

public record Firma(String name, int firmenID, int anzahlWuensche, List<Zeitslot> verfuegbareZeiten,
                    List<Zeitslot> gebuchteZeitslots) {

}
