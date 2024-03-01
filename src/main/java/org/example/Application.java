package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.controller.ApplicationController;
import org.example.controller.FirmaController;
import org.example.controller.SchuelerController;
import org.example.controller.ZeitslotController;
import org.example.fileUtils.FirmaSerialize;
import org.example.fileUtils.RaumSerialize;
import org.example.fileUtils.SchuelerSerialize;
import org.example.gui.FirmaPanel;
import org.example.gui.MainFrame;
import org.example.gui.SchuelerPanel;
import org.example.models.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Application {
    public static void main(String[] args) throws IOException {
        FlatDarkLaf.setup();

        //List<Firma> firmen = FirmaSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT1_Veranstaltungsliste.xlsx"));
        FirmaList firmaList = new FirmaList(List.of());
        FirmaPanel firmaPanel = new FirmaPanel();
        FirmaController firmaController = new FirmaController(firmaList, firmaPanel);

        //List<Schueler> schueler = SchuelerSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT2_Wahl.xlsx"));
        SchuelerList schuelerList = new SchuelerList(List.of());
        SchuelerPanel schuelerPanel = new SchuelerPanel();
        SchuelerController schuelerController = new SchuelerController(schuelerList, schuelerPanel);

        ZeitslotController zeitslotController = new ZeitslotController();
        List<Raum> raumList = RaumSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT0_Raumliste.xlsx"));

        MainFrame mainFrame = new MainFrame(schuelerPanel, firmaPanel);

        ApplicationController applicationController = new ApplicationController(firmaController, schuelerController, zeitslotController, mainFrame);
    }
}