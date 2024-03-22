package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Zeitslot {

    private Firma firma;

    private List<String> schuelerList;

}
