package org.example.models;

import lombok.Data;

import java.util.List;

@Data
public class Raum {
    String name;

    List<Zeitslot> zeitslots;
}
