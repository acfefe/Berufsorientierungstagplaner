package org.example.fileUtils.readers;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Schueler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SchuelerSerialize {

    /**
     * Liest Excel Dateien in eine Liste ein
     * @param path Ein Pfad zur einzulesenden Liste
     * @return Eine Liste vom Typ Schüler um diese einzulesen
     * @throws IOException
     * @Author Justin
     */
    public static List<Schueler> readExcelIntoList(Path path) throws IOException {
        File fileTest = path.toFile();
        if (!fileTest.exists()) {
            return new ArrayList<>();
        }
        List<Schueler> schuelerList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path.toFile());
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                Schueler schueler = new Schueler();
                schueler.setKlasse(row.getCellText(0));
                schueler.setNachname(row.getCellText(1));
                schueler.setVorname(row.getCellText(2));
                int[] wahlen = new int[6];
                for (int i = 0; i < wahlen.length; i++) {
                    String cellText = row.getCellText(i + 3);
                    wahlen[i] = Integer.parseInt(!cellText.isEmpty() ? cellText : "0");
                }
                schueler.setWahlen(wahlen);
                schuelerList.add(schueler);
            }
        }
        return schuelerList;
    }

}
