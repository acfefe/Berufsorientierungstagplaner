package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Zeitslot {
    Firma firma;

    List<Schueler> schuelerList;
}
