package org.example.fileUtils;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Firma;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FirmaSerialize {

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

    public static void writeExcelIntoList(List<Firma> data, Path path) throws IOException {
        File file = path.toFile();
        if (!file.exists())
            return;

        try (OutputStream os = new FileOutputStream(file)) {
            Workbook wb = new Workbook(os, /*f.getName()*/"Firmen", "1.0");
            for (Firma f : data) {
                Worksheet ws = wb.newWorksheet("Firmenname");
                ws.value(0, 0, "Anwesenheitsliste");
                ws.value(1, 0, "Firmenname");
                ws.value(2, 0, "Zeitslot 1");
            }
            wb.finish();
        }


    }
}
