package org.example.fileUtils.readers;

import lombok.Data;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Raum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class RaumSerialize {

    /**
     * Liest Excel Dateien in eine Liste ein
     * @param path Ein Pfad zur einzulesenden Liste
     * @return Eine Liste vom Typ Raum um diese einzulesen
     * @throws IOException
     * @Author Justin
     */
    public static List<Raum> readExcelIntoList(Path path) throws IOException {
        File fileTest = path.toFile();
        if (!fileTest.exists()) {
            return new ArrayList<>();
        }
        List<Raum> raumList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path.toFile());
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            sheet.read().subList(1, sheet.read().size()).forEach(row -> {
                Raum raum = new Raum(row.getCellText(0), Integer.parseInt(row.getCellText(1)));
                raumList.add(raum);
            });
        }
        return raumList;
    }


}

