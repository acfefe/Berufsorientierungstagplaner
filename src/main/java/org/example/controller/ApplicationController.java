package org.example.controller;

import lombok.Data;
import org.example.models.Firma;
import org.example.models.Schueler;

import java.util.Arrays;

@Data
public class ApplicationController {

    private final FirmaController firmaController;
    private final SchuelerController schuelerController;


    public void calculateWishNumber(SchuelerController schuelerController, FirmaController firmaController) {
        for(Firma firma : firmaController.getFirmaList().getFirmaList()) {
            int anzahlWuensche = 0;
            for(Schueler schueler: schuelerController.getSchuelerList().getSchuelerList()) {
                if(Arrays.stream(schueler.getWahlen()).anyMatch(whl ->  whl == firma.getFirmenId())) {
                    anzahlWuensche++;
                }
            }
            firma.setAnzahlWuensche(anzahlWuensche);
        }
    }

    public void assignRooms() {

    }
}
