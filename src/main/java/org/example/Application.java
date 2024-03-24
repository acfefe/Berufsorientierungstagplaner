package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import org.example.controller.*;
import org.example.fileUtils.readers.FirmaSerialize;
import org.example.fileUtils.readers.RaumSerialize;
import org.example.fileUtils.readers.SchuelerSerialize;
import org.example.gui.FirmaPanel;
import org.example.gui.MainFrame;
import org.example.gui.RaumPanel;
import org.example.gui.SchuelerPanel;
import org.example.models.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;


public class Application {
    public static void main(String[] args) throws IOException {
        Properties appProp = new Properties();
        ClassLoader classLoader = Application.class.getClassLoader();
        appProp.load(classLoader.getResourceAsStream("application.properties"));
        
        // Replace placeholders in property file
        for (String key : appProp.stringPropertyNames()) {
            String value = appProp.getProperty(key);
            value = value.replace("${user.dir}", System.getProperty("user.dir"));
            appProp.setProperty(key, value);
        }

        FlatDarkLaf.setup();

        List<Firma> firmen = FirmaSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.veranstaltungs.datei")));
        FirmaList firmaList = new FirmaList(firmen);

        FirmaPanel firmaPanel = new FirmaPanel();
        FirmaController firmaController = new FirmaController(firmaList, firmaPanel);

        List<Schueler> schueler = SchuelerSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.schueler.datei")));
        SchuelerList schuelerList = new SchuelerList(schueler);
        SchuelerPanel schuelerPanel = new SchuelerPanel();
        SchuelerController schuelerController = new SchuelerController(schuelerList, schuelerPanel);

        ZeitslotController zeitslotController = new ZeitslotController();

        List<Raum> raeume = RaumSerialize.readExcelIntoList(Path.of(appProp.getProperty("app.raum.datei")));
        RaumList raumList = new RaumList(raeume);
        RaumPanel raumPanel = new RaumPanel();
        RaumController raumController = new RaumController(raumList, raumPanel);

        MainFrame mainFrame = new MainFrame(schuelerPanel, firmaPanel, raumPanel);

        ApplicationController applicationController = new ApplicationController(firmaController, schuelerController, zeitslotController, raumController, mainFrame);
    }
}