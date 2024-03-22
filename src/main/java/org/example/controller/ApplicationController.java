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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

/**
 * Die ApplicationController-Klasse ist verantwortlich für die Steuerung des Anwendungsflusses und die Verwaltung der Interaktion zwischen verschiedenen Controllern und dem Hauptfenster.
 * Sie lädt Daten aus Excel-Dateien, führt Berechnungen durch und weist Unternehmen basierend auf bestimmten Kriterien Räume zu.
 * Die Klasse bietet auch Methoden zum Öffnen und Lesen von Excel-Dateien für Schüler-, Firmen- und Raumdaten.
 */
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
    private final Path userConfigPath = Paths.get(System.getProperty("user.home"), "Berufsorientierungstagsplaner", "application.properties");
        

    public ApplicationController(FirmaController firmaController, SchuelerController schuelerController, ZeitslotController zeitslotController, RaumController raumController, MainFrame mainFrame) throws IOException {
        this.firmaController = firmaController;
        this.schuelerController = schuelerController;
        this.zeitslotController = zeitslotController;
        this.raumController = raumController;
        this.mainFrame = mainFrame;
        this.schuelerController.loadSchueler();
        this.firmaController.loadFirma();
        this.raumController.loadRaum();
        this.mainFrame.getLoadSchueler().addActionListener(e -> openSchuelerFile());
        this.mainFrame.getLoadFirma().addActionListener(e -> openFirmaFile());
        this.mainFrame.getLoadRaum().addActionListener(e -> openRaumFile());
        startAlgorithm();
        Files.createDirectories(userConfigPath.getParent()); // Ensure the directory exists
    }

    /**
     * Berechnet die Anzahl der gewünschten Schüler für jede Firma.
     * Die Methode durchläuft alle Firmen und zählt die Anzahl der Schüler, die die Firma als Wunsch gewählt haben.
     * Die berechnete Anzahl wird in der Firma gespeichert.
     * @Author Felix, Finn
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

    /**
     * Ein Teil des Algorithmus, der Firmen zu räumen zu teilt
     * @Author Felix, Finn
     */
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
        SwingUtilities.invokeLater(() -> { // Ensure Swing components are accessed on the Event Dispatch Thread. -> THREAD SAFETY!!!
            openFile(
                "Wähle die Schueler-Excel-Liste aus",
                "app.schueler.datei",
                selectedPath -> {
                    try {
                        this.schuelerController.getSchuelerList().setSchueler(SchuelerSerialize.readExcelIntoList(selectedPath));
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "An error occurred while opening the file: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    this.schuelerController.loadSchueler();
                }
            );
        });
    }

    public void openRaumFile() {
        SwingUtilities.invokeLater(() -> { // Ensure Swing components are accessed on the Event Dispatch Thread. -> THREAD SAFETY!!!
            openFile(
                "Wähle die Raum-Excel-Liste aus",
                "app.raum.datei",
                selectedPath -> {
                    try {
                        this.raumController.getRaumList().setRaumList(RaumSerialize.readExcelIntoList(selectedPath));
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "An error occurred while opening the file: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    this.raumController.loadRaum();
                }
            );
        });
    }

    public void openFirmaFile() {
        SwingUtilities.invokeLater(() -> { // Ensure Swing components are accessed on the Event Dispatch Thread. -> THREAD SAFETY!!!
            openFile(
                "Wähle die Veranstaltungs-Excel-Liste aus",
                "app.veranstaltungs.datei", 
                selectedPath -> {
                    try {
                        this.firmaController.getFirmaList().setFirmen(FirmaSerialize.readExcelIntoList(selectedPath));
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "An error occurred while opening the file: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    this.firmaController.loadFirma();
                }
            );
        });
    }
    

    /**
     * Öffnet einen FileChooser um die Schülerliste einzulesen
     * @Author Finn & Justin
     */
/*
    public void openSchuelerFile() {
        this.mainFrame.getLoadSchueler().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Schueler-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.schuelerPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    if (Files.exists(userConfigPath)) {
                        try (InputStream userPropStream = Files.newInputStream(userConfigPath)) {
                            appProp.load(userPropStream);
                        }
                    } 
                    appProp.setProperty("app.schueler.datei", String.valueOf(this.schuelerPath));
                    appProp.store(new FileOutputStream(userConfigPath.toFile()), null);

                    this.schuelerController.getSchuelerList().setSchueler(SchuelerSerialize.readExcelIntoList(this.schuelerPath));
                    this.schuelerController.loadSchueler();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        "An error occurred while opening the file: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );                
                }
            }


        });
    }
*/


    /**
     * Öffnet einen FileChooser um die Firmenliste einzulesen
     * @Author Finn & Justin
     */
/*
    public void openFirmaFile() {
        this.mainFrame.getLoadFirma().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Firmen-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.firmaPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    if (Files.exists(userConfigPath)) {
                        try (InputStream userPropStream = Files.newInputStream(userConfigPath)) {
                            appProp.load(userPropStream);
                        }
                    } 
                    appProp.setProperty("app.veranstaltungs.datei", String.valueOf(this.firmaPath));
                    appProp.store(new FileOutputStream(userConfigPath.toFile()), null);

                    this.firmaController.getFirmaList().setFirmen(FirmaSerialize.readExcelIntoList(this.firmaPath));
                    this.firmaController.loadFirma();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        "An error occurred while opening the file: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
*/


    /**
     * Öffnet ein FileChooser um die RaumDatei einzulesen
     * @Author Finn & Justin
     */
/*
     public void openRaumFile() {
        this.mainFrame.getLoadRaum().addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));

            jFileChooser.setAcceptAllFileFilterUsed(false);

            jFileChooser.setDialogTitle("Wähle die Raum-Excel-Liste aus");

            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
            jFileChooser.addChoosableFileFilter(restrict);

            int opt = jFileChooser.showOpenDialog(null);

            if (opt == JFileChooser.APPROVE_OPTION) {
                this.raumPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

                try {
                    if (Files.exists(userConfigPath)) {
                        try (InputStream userPropStream = Files.newInputStream(userConfigPath)) {
                            appProp.load(userPropStream);
                        }
                    }                    
                    appProp.setProperty("app.raum.datei", String.valueOf(this.raumPath));
                    appProp.store(new FileOutputStream(userConfigPath.toFile()), null);
                    this.raumController.getRaumList().setRaumList(RaumSerialize.readExcelIntoList(this.raumPath));
                    this.raumController.loadRaum();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        "An error occurred while opening the file: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
*/

private void openFile(String dialogTitle, String propertyKey, Consumer<Path> afterOpenAction) {
    JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));

    jFileChooser.setAcceptAllFileFilterUsed(false);
    jFileChooser.setDialogTitle(dialogTitle);

    FileNameExtensionFilter restrict = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
    jFileChooser.addChoosableFileFilter(restrict);

    int opt = jFileChooser.showOpenDialog(null);

    if (opt == JFileChooser.APPROVE_OPTION) {
        Path selectedPath = Path.of(jFileChooser.getSelectedFile().getAbsolutePath());

        try {
            if (Files.exists(userConfigPath)) {
                try (InputStream userPropStream = Files.newInputStream(userConfigPath)) {
                    appProp.load(userPropStream);
                }
            }
            appProp.setProperty(propertyKey, String.valueOf(selectedPath));
            try (FileOutputStream fos = new FileOutputStream(userConfigPath.toFile())) {
                appProp.store(fos, null);
            }

            afterOpenAction.accept(selectedPath);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                null,
                "An error occurred while opening the file: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

    /**
     * Startet den Algorithmus auf Knopfdruck
     * @Author Justin
     */
    public void startAlgorithm() {
        this.mainFrame.getStartBtn().addActionListener(e -> {
            this.calculateWishNumber();
            this.assignRooms();
        });
    }
}
