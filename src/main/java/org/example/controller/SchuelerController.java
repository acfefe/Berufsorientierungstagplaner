package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gui.SchuelerPanel;
import org.example.models.SchuelerList;

import java.util.Arrays;

@Data
@AllArgsConstructor
public class SchuelerController {

    private SchuelerList schuelerList;
    private SchuelerPanel schuelerPanel;

    public void loadSchueler() {
        this.schuelerList.getSchueler().forEach(row -> {
            this.schuelerPanel.getSchuelerTableModel().addRow(new Object[]{
                    row.getKlasse(),
                    row.getNachname(),
                    row.getVorname(),
                    Arrays.toString(row.getWahlen())
            });
        });
    }
}
