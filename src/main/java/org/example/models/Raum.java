package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Raum {

    private String name;
    private Zeitslot[] zeitslots = new Zeitslot[5];

    public Raum(String name) {
        this.name = name;
    }

}
