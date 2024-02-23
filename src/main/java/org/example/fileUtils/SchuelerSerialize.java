package org.example.fileUtils;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Schueler;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SchuelerSerialize {

    public static List<Schueler> readExcelIntoList(Path path) throws IOException {
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
                int[] wahlen = {
                        Integer.parseInt(row.getCellText(3)),
                        Integer.parseInt(row.getCellText(4)),
                        Integer.parseInt(row.getCellText(5)),
                        Integer.parseInt(row.getCellText(6)),
                        Integer.parseInt(row.getCellText(7)),
                        Integer.parseInt(row.getCellText(8)),

                };
                schueler.setWahlen(wahlen);
                schuelerList.add(schueler);
            }
        }
        return schuelerList;
    }

}
