package org.example.controller;

import lombok.Data;
import org.example.gui.RaumPanel;
import org.example.models.RaumList;

@Data
public class RaumController {

    private RaumList raumList;
    private RaumPanel raumPanel;

    public RaumController(RaumList raumList, RaumPanel raumPanel) {
        this.raumList = raumList;
        this.raumPanel = raumPanel;
    }

    public void loadRaum() {
        this.raumList.getRaumList().forEach(row -> {
            this.raumPanel.getRaumTableModel().addRow(new Object[]{
                    row.getName()
            });
        });
    }
}
