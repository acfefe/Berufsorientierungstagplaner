package org.example;

import org.example.fileUtils.FirmaSerialize;
import org.example.fileUtils.RaumSerialize;
import org.example.fileUtils.SchuelerSerialize;
import org.example.models.Firma;
import org.example.models.Raum;
import org.example.models.Schueler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Application {
    public static void main(String[] args) throws IOException {
        List<Raum> raumList = RaumSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT0_Raumliste.xlsx"));
        System.out.println(raumList);
        List<Firma> firmaList = FirmaSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT1_Veranstaltungsliste.xlsx"));
        System.out.println(firmaList);
        List<Schueler> schuelerList = SchuelerSerialize.readExcelIntoList(Path.of("/home/justin/Schreibtisch/Code/Java/Berufsorientierungstagplaner/src/main/resources/IMPORT BOT2_Wahl.xlsx"));
    }
}