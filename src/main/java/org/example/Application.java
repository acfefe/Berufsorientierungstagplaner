package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import org.example.controller.*;
import org.example.fileUtils.FirmaSerialize;
import org.example.fileUtils.RaumSerialize;
import org.example.fileUtils.SchuelerSerialize;
import org.example.gui.FirmaPanel;
import org.example.gui.MainFrame;
import org.example.gui.RaumPanel;
import org.example.gui.SchuelerPanel;
import org.example.models.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;


public class Application {
    public static void main(String[] args) throws IOException {
        Properties appProp = new Properties();
        appProp.load(new FileInputStream("config/application.properties"));


        FlatDarkLaf.setup();

        List<Firma> firmen = FirmaSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.veranstaltungs.datei")));
//        List<Firma> firmen = FirmaSerialize.readExcelIntoList(Path.of("/home/justin/Repos/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT1_Veranstaltungsliste.xlsx"));
//        List<Firma> firmen = FirmaSerialize.readExcelIntoList(Path.of("H:\\IdeaProjects\\Projekt\\src\\main\\resources\\IMPORT BOT1_Veranstaltungsliste.xlsx"));
        FirmaList firmaList = new FirmaList(firmen);
        FirmaPanel firmaPanel = new FirmaPanel();
        FirmaController firmaController = new FirmaController(firmaList, firmaPanel);

        List<Schueler> schueler = SchuelerSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.schueler.datei")));
//        List<Schueler> schueler = SchuelerSerialize.readExcelIntoList(Path.of("/home/justin/Repos/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT2_Wahl.xlsx"));
//        List<Schueler> schueler = SchuelerSerialize.readExcelIntoList(Path.of("H:\\IdeaProjects\\Projekt\\src\\main\\resources\\IMPORT BOT2_Wahl.xlsx"));
        SchuelerList schuelerList = new SchuelerList(schueler);
        SchuelerPanel schuelerPanel = new SchuelerPanel();
        SchuelerController schuelerController = new SchuelerController(schuelerList, schuelerPanel);

        ZeitslotController zeitslotController = new ZeitslotController();

        List<Raum> raeume = RaumSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.raum.datei")));
//        List<Raum> raeume = RaumSerialize.readExcelIntoList(Path.of("/home/justin/Repos/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT0_Raumliste.xlsx"));
//        List<Raum> raeume = RaumSerialize.readExcelIntoList(Path.of("H:\\IdeaProjects\\Projekt\\src\\main\\resources\\IMPORT BOT0_Raumliste.xlsx"));
        RaumList raumList = new RaumList(raeume);
        RaumPanel raumPanel = new RaumPanel();
        RaumController raumController = new RaumController(raumList, raumPanel);


        MainFrame mainFrame = new MainFrame(schuelerPanel, firmaPanel, raumPanel);

        ApplicationController applicationController = new ApplicationController(firmaController, schuelerController, zeitslotController, raumController, mainFrame);
    }
}