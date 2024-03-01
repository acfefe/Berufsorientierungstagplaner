package org.example.controller;

import lombok.Data;
import org.example.gui.SchuelerPanel;
import org.example.models.SchuelerList;

import java.util.Arrays;

@Data
public class SchuelerController {

    private SchuelerList schuelerList;
    private SchuelerPanel schuelerPanel;

    public SchuelerController(SchuelerList schuelerList, SchuelerPanel schuelerPanel) {
        this.schuelerList = schuelerList;
        this.schuelerPanel = schuelerPanel;
    }

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
