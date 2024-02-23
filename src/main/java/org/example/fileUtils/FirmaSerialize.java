package org.example.fileUtils;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Firma;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FirmaSerialize {

    public static List<Firma> readExcelIntoList(Path path) throws IOException {
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
