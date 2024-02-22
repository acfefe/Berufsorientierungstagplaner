package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Zeitslot {

    private Firma firma;
    private List<Schueler> schuelerList;
}
