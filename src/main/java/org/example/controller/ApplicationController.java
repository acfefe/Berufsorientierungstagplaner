package org.example.controller;

import lombok.Data;
import org.example.fileUtils.FirmaSerialize;
import org.example.fileUtils.RaumSerialize;
import org.example.fileUtils.SchuelerSerialize;
import org.example.gui.MainFrame;
import org.example.models.Firma;
import org.example.models.Raum;
import org.example.models.Schueler;
import org.example.models.Zeitslot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
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

    /*
    public double calcMaxScore() {
        double maxScore = 0;
        int test = 0;
        for (Schueler student : schuelerController.getSchuelerList().getSchueler()) {
            test++;
            for (int wish : student.getWahlen()) {
                int counter = wish.getPrio();
                if (counter == 1) {
                    if (wish.getCompID() != -1) {
                        maxScore += 6;
                    } else {
                        maxScore += 0;
                    }
                } else if (counter == 2) {
                    if (wish.getCompID() != -1) {
                        maxScore += 5;
                    } else {
                        maxScore += 0;
                    }
                } else if (counter == 3) {
                    if (wish.getCompID() != -1) {
                        maxScore += 4;
                    } else {
                        maxScore += 0;
                    }
                } else if (counter == 4) {
                    if (wish.getCompID() != -1) {
                        maxScore += 3;
                    } else {
                        maxScore += 0;
                    }
                } else if (counter == 5) {
                    if (wish.getCompID() != -1) {
                        maxScore += 2;
                    } else {
                        maxScore += 0;
                    }
                }
            }
        }
        return maxScore;
    }


    public double calcScore() {
        double maxScore = calcMaxScore();
        double realScore = 0;
        for (Schueler student : schuelerController.getSchuelerList().getSchueler()) {
            for (int wish : student.getWahlen()) {
                for (Event toGoEvent : student.toGolist) {
                    int wishId = wish.getCompID();
                    if (wishId == toGoEvent.eventid) {
                        int counter = wish.getPrio();
                        if (counter == 1) {
                            if (wish.getCompID() != -1) {
                                realScore += 6;
                            } else {
                                realScore += 0;
                            }
                        } else if (counter == 2) {
                            if (wish.getCompID() != -1) {
                                realScore += 5;
                            } else {
                                realScore += 0;
                            }
                        } else if (counter == 3) {
                            if (wish.getCompID() != -1) {
                                realScore += 4;
                            } else {
                                realScore += 0;
                            }
                        } else if (counter == 4) {
                            if (wish.getCompID() != -1) {
                                realScore += 3;
                            } else {
                                realScore += 0;
                            }
                        } else {
                            if (wish.getCompID() != -1) {
                                realScore += 2;
                            } else {
                                realScore += 0;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(realScore);
        System.out.println(maxScore);
        return (realScore / maxScore) * 100;
    }

    */
    public void calculateWishNumber() {
        for (Firma firma : firmaController.getFirmaList().getFirmen()) {
            int anzahlWuensche = 0;
            for (Schueler schueler : schuelerController.getSchuelerList().getSchueler()) {
                int[] wahlen = schueler.getWahlen();
                for (int whl : wahlen) {
                    if (whl == firma.getFirmenID()) {
                        anzahlWuensche++;
                        break; // No need to continue iteration once the condition is met
                    }
                }
            }
            firma.setAnzahlWuensche(anzahlWuensche);
        }
    }

    public void assignRooms() {
        List<Raum> raumListe = raumController.getRaumList().getRaumList();
        List<Firma> firmen = firmaController.getFirmaList().getFirmen();

        for (Firma firma : firmen) {
            int anzahlVeranstaltungen = (int) Math.ceil((double) firma.getAnzahlWuensche() / (double) firma.getMaximaleAnzahlSchueler());
            firma.setAnzahlVeranstaltung(anzahlVeranstaltungen);
            firma.setGebuchteZeitslots(new ArrayList<>());
        }

        firmen.sort(Comparator.comparingInt(Firma::getAnzahlVeranstaltung).reversed());

        for (Firma firma : firmen) {
            List<String> gebuchteZeitslots = firma.getGebuchteZeitslots();
            for (Raum raum : raumListe) {
                Zeitslot[] zeitslots = raum.getZeitslots();
                for (int j = 0; j < zeitslots.length && firma.getAnzahlVeranstaltung() > 0; j++) {
                    if (zeitslots[j] == null) {
                        Zeitslot zeitslot = new Zeitslot(firma, gebuchteZeitslots);
                        gebuchteZeitslots.add(zeitslot.toString());
                        zeitslots[j] = zeitslot;
                        firma.setAnzahlVeranstaltung(firma.getAnzahlVeranstaltung() - 1);
                    }
                }
                raum.setZeitslots(zeitslots);
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

                    appProp.load(new FileInputStream("config/application.properties"));
                    appProp.setProperty("app.schueler.datei", String.valueOf(this.schuelerPath));
<<<<<<< HEAD
                    appProp.store(new FileOutputStream("config/application.properties"), null);
=======
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.schuelerController.getSchuelerList().setSchueler(SchuelerSerialize.readExcelIntoList(this.schuelerPath));
>>>>>>> baf2447c480d8d018ed8b4e4901db0246aa05f31
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
                    appProp.load(new FileInputStream("config/application.properties"));
                    appProp.setProperty("app.veranstaltungs.datei", String.valueOf(this.firmaPath));
<<<<<<< HEAD
                    appProp.store(new FileOutputStream("config/application.properties"), null);

=======
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.firmaController.getFirmaList().setFirmen(FirmaSerialize.readExcelIntoList(this.firmaPath));
>>>>>>> baf2447c480d8d018ed8b4e4901db0246aa05f31
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

            jFileChooser.setDialogTitle("Wähle die Raum-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".xlsx", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.raumPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    appProp.load(new FileInputStream("config/application.properties"));
                    appProp.setProperty("app.raum.datei", String.valueOf(this.raumPath));
<<<<<<< HEAD
                    appProp.store(new FileOutputStream("config/application.properties"), null);

=======
                    appProp.store(fileOut, null);
                    fileIn.close();
                    fileOut.close();
                    this.raumController.getRaumList().setRaumList(RaumSerialize.readExcelIntoList(this.raumPath));
>>>>>>> baf2447c480d8d018ed8b4e4901db0246aa05f31
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
