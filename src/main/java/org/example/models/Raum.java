package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Raum {

    private String name;
    private Zeitslot[] zeitslots = new Zeitslot[5];
    private int capacity;

    public Raum(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

}
