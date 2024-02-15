package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Firma {
    String name;

    int firmenId;

    int anzahlWuensche;

    List<Zeitslot> verfügbareZeitslots;

    List<Zeitslot>  gebuchteZeitslots;
}
