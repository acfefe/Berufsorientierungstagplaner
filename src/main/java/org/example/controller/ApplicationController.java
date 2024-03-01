package org.example.controller;

import lombok.Data;
import org.example.gui.MainFrame;
import org.example.models.Firma;
import org.example.models.Schueler;

import java.util.Arrays;

@Data
public class ApplicationController {

    private final FirmaController firmaController;
    private final SchuelerController schuelerController;
    private final ZeitslotController zeitslotController;
    private final MainFrame mainFrame;

    public ApplicationController(FirmaController firmaController, SchuelerController schuelerController, ZeitslotController zeitslotController, MainFrame mainFrame) {
        this.firmaController = firmaController;
        this.schuelerController = schuelerController;
        this.zeitslotController = zeitslotController;
        this.mainFrame = mainFrame;
        this.schuelerController.loadSchueler();
        this.firmaController.loadFirma();
    }

    public void calculateWishNumber(SchuelerController schuelerController, FirmaController firmaController) {
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {
            int anzahlWuensche = 0;
            for (Schueler schueler : schuelerController.getSchuelerList().getSchueler()) {
                if (Arrays.stream(schueler.getWahlen())
                        .anyMatch(whl ->
                                whl == firma.getFirmenID())) {
                    anzahlWuensche++;
                }
            }
            firma.setAnzahlWuensche(anzahlWuensche);
        }
    }

    public void assignRooms() {
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {
            int anzahlVeranstaltungen = (int) Math.ceil(firma.getAnzahlWuensche() / firma.getMaximaleAnzahlSchueler());
        }
    }
}
