package org.example.controller;

import lombok.Data;
import org.example.gui.MainFrame;
import org.example.models.Firma;
import org.example.models.Raum;
import org.example.models.Schueler;
import org.example.models.Zeitslot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;

@Data
public class ApplicationController {

    private final FirmaController firmaController;
    private final SchuelerController schuelerController;
    private final ZeitslotController zeitslotController;
    private final RaumController raumController;
    private final MainFrame mainFrame;
    private Path firmaPath;
    private Path schuelerPath;

    public ApplicationController(FirmaController firmaController, SchuelerController schuelerController, ZeitslotController zeitslotController, RaumController raumController, MainFrame mainFrame) {
        this.firmaController = firmaController;
        this.schuelerController = schuelerController;
        this.zeitslotController = zeitslotController;
        this.raumController = raumController;
        this.mainFrame = mainFrame;
        openSchuelerFile();
        this.schuelerController.loadSchueler();
        this.firmaController.loadFirma();
        this.raumController.loadRaum();
        openSchuelerFile();
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
        List<Raum> raumListe = raumController.getRaumList().getRaumList();
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {
            int anzahlVeranstaltungen = (int) Math.ceil(firma.getAnzahlWuensche() / firma.getMaximaleAnzahlSchueler());
            firma.setAnzahlVeranstaltung(anzahlVeranstaltungen);
        }
        Collections.sort(firmaController.getFirmaList().getFirmen(), Comparator.comparingInt(Firma::getAnzahlVeranstaltung));
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {
            for(int i = 0;i < firma.getAnzahlVeranstaltung();) {
                for (Raum raum:
                     raumListe) {
                    Zeitslot[] zeitslots = raum.getZeitslots();
                    for (int j = 0;j < zeitslots.length;j++) {
                        if(zeitslots[j] == null) {
                            zeitslots[j] = new Zeitslot(firma, raum, new ArrayList<>());
                            i++;
                        }
                    }
                }
            }
        }
        raumController.getRaumList().setRaumList(raumListe);
    }

    public void openSchuelerFile() {
        this.mainFrame.getLoadSchueler().addActionListener( e -> {
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Schueler-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION)
                this.schuelerPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());
        });
    }

    public void openFirmaFile() {
        this.mainFrame.getLoadFirma().addActionListener(e->{
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Firmen-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION)
                this.firmaPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());
        });
    }
}
