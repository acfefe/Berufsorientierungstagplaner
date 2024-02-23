package org.example.fileUtils;

import lombok.Data;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.models.Raum;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class RaumSerialize {

    public static List<Raum> readExcelIntoList(Path path) throws IOException {
        List<Raum> raumList = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(path.toFile());
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            sheet.read().forEach(row -> {
                Raum raum = new Raum(row.getCellText(0));
                raumList.add(raum);
            });
        }
        return raumList;
    }
}
