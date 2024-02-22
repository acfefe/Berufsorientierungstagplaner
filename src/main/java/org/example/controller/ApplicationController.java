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
        for(Firma firma : firmaController.getFirmaList().firmen()) {
            int anzahlWuensche = 0;
            for(Schueler schueler: schuelerController.getSchuelerList().schueler()) {
                if(Arrays.stream(schueler.wahlen()).anyMatch(whl ->  whl == firma.firmenID())) {
                    anzahlWuensche++;
                }
            }
            firma.anzahlWuensche(anzahlWuensche);
        }
    }

    public void assignRooms() {
        for(Firma firma : firmaController.getFirmaList().firmen()) {
            int anzahlVeranstaltungen = firma.anzahlWuensche() / firma.maximaleAnzahlSchueler();
        }
    }
}
