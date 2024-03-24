package org.example.fileUtils.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.example.models.FirmaList;
import org.example.models.Firma;

public class RoomAssignmentsWriter {
        public static void writeRoomAssignments(FirmaList firmaList, String filename) throws FileNotFoundException, IOException {
        try (OutputStream os = new FileOutputStream(filename); Workbook wb = new Workbook(os, filename, "1.0")) {
            Worksheet ws = wb.newWorksheet("Room Assignments");
            ws.value(0, 2, "8:45 - 9:30\nA");
            ws.value(0, 3, "9:50 - 10:35\nB");
            ws.value(0, 4, "10:35 - 11:20\nC");
            ws.value(0, 5, "11:40 - 12:25\nD");
            ws.value(0, 6, "12:25 - 13:10\nE");

            for (int i = 0; i < firmaList.getFirmen().size(); i++) {
                ws.value(i + 1, 0, firmaList.getFirmen().get(i).getFirmenID());
                ws.value(i + 1, 1, firmaList.getFirmen().get(i).getName());
                List<Firma.Veranstaltung> veranstaltungen = firmaList.getFirmen().get(i).getVeranstaltungen();
                for (Firma.Veranstaltung veranstaltung : veranstaltungen) {
                   switch (veranstaltung.getZeitslot()) {
                        case "A":
                            ws.value(i + 1, 2, veranstaltung.getRaum().getName());
                            break;
                        case "B":
                            ws.value(i + 1, 3, veranstaltung.getRaum().getName());
                            break;
                        case "C":
                            ws.value(i + 1, 4, veranstaltung.getRaum().getName());
                            break;
                        case "D":
                            ws.value(i + 1, 5, veranstaltung.getRaum().getName());
                            break;
                        case "E":
                            ws.value(i + 1, 6, veranstaltung.getRaum().getName());
                            break;
                   }
                }
            }
        }
    }
}

