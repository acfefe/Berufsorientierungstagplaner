package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Raum {

    private String name;
    private List<Zeitslot> zeitslots;

    public Raum(String name) {
        this.name = name;
    }

}
