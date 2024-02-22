package org.example.models;

import java.util.List;

public record Raum(String name, List<Zeitslot> zeitslots) {

}
