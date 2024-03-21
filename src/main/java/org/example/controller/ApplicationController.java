package org.example.controller;

import lombok.Data;
import org.example.gui.MainFrame;
import org.example.models.Firma;
import org.example.models.Raum;
import org.example.models.Schueler;
import org.example.models.Zeitslot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Data
public class ApplicationController {

    private final FirmaController firmaController;
    private final SchuelerController schuelerController;
    private final ZeitslotController zeitslotController;
    private final RaumController raumController;
    private final MainFrame mainFrame;
    private Path firmaPath;
    private Path raumPath;
    private Path schuelerPath;
    private Properties appProp = new Properties();

    public ApplicationController(FirmaController firmaController, SchuelerController schuelerController, ZeitslotController zeitslotController, RaumController raumController, MainFrame mainFrame) throws IOException {
        this.firmaController = firmaController;
        this.schuelerController = schuelerController;
        this.zeitslotController = zeitslotController;
        this.raumController = raumController;
        this.mainFrame = mainFrame;
        openSchuelerFile();
        openFirmaFile();
        openRaumFile();
        startAlgorithm();
    }

    public void calculateWishNumber() {
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
            int anzahlVeranstaltungen = (int) Math.ceil((double) firma.getAnzahlWuensche() / (double) firma.getMaximaleAnzahlSchueler());
            firma.setAnzahlVeranstaltung(anzahlVeranstaltungen);
            firma.setGebuchteZeitslots(new ArrayList<>());
        }
        firmaController.getFirmaList().getFirmen().sort(Comparator.comparingInt(Firma::getAnzahlVeranstaltung).reversed());
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {

            for (int i = 0; i < firma.getAnzahlVeranstaltung(); ) {
                for (Raum raum : raumListe) {
                    Zeitslot[] zeitslots = raum.getZeitslots();
                    for (int j = 0; j < zeitslots.length; j++) {
                        if (zeitslots[j] == null) {
                            Zeitslot zeitslot = new Zeitslot(firma, new ArrayList<>());
                            ArrayList<String> gebuchteZeitslots = firma.getGebuchteZeitslots();
                            gebuchteZeitslots.add(zeitslot.toString());
                            firma.setGebuchteZeitslots(gebuchteZeitslots);
                            zeitslots[j] = zeitslot;
                            i++;
                        }
                        raum.setZeitslots(zeitslots);
                    }
                }
            }
        }
        raumController.getRaumList().setRaumList(raumListe);
        System.out.println(raumListe);
    }

    public void openSchuelerFile() {
        this.mainFrame.getLoadSchueler().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Schueler-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.schuelerPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    FileInputStream fileIn = new FileInputStream("config/application.properties");
                    FileOutputStream fileOut = new FileOutputStream("config/application.properties");
                    appProp.load(fileIn);
                    appProp.setProperty("app.schueler.datei", String.valueOf(this.schuelerPath));
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.schuelerController.loadSchueler();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
    }

    public void openFirmaFile() {
        this.mainFrame.getLoadFirma().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Firmen-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.firmaPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    FileInputStream fileIn = new FileInputStream("config/application.properties");
                    FileOutputStream fileOut = new FileOutputStream("config/application.properties");
                    appProp.load(fileIn);
                    appProp.setProperty("app.veranstaltungs.datei", String.valueOf(this.firmaPath));
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.firmaController.loadFirma();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void openRaumFile() {
        this.mainFrame.getLoadRaum().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Firmen-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.raumPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    FileInputStream fileIn = new FileInputStream("config/application.properties");
                    FileOutputStream fileOut = new FileOutputStream("config/application.properties");
                    appProp.load(fileIn);
                    appProp.setProperty("app.raum.datei", String.valueOf(this.raumPath));
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.raumController.loadRaum();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void startAlgorithm() {
        this.mainFrame.getStartBtn().addActionListener(e -> {
            this.calculateWishNumber();
            this.assignRooms();
        });
    }
}
