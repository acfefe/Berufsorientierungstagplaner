package org.example.fileUtils.readers;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Firma;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FirmaSerialize {

    /**
     * Liest Excel Dateien in eine Liste ein
     * @param path Ein Pfad zur einzulesenden Liste
     * @return Eine Liste vom Typ Firma um diese einzulesen
     * @throws IOException
     * @Author Justin
     */
    public static List<Firma> readExcelIntoList(Path path) throws IOException {
        File fileTest = path.toFile();
        if (!fileTest.exists()) {
            return new ArrayList<>();
        }
        List<Firma> FirmaList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path.toFile());
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                Firma firma = new Firma();
                firma.setFirmenID(Integer.parseInt(row.getCellText(0)));
                firma.setName(row.getCellText(1));
                firma.setMaximaleAnzahlSchueler(Integer.parseInt(row.getCellText(3)));
                FirmaList.add(firma);
            }
        }
        return FirmaList;
    }
}
