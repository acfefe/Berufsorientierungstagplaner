package org.example.controller;

import lombok.Data;
import org.example.gui.FirmaPanel;
import org.example.models.FirmaList;

@Data
public class FirmaController {

    private FirmaList firmaList;
    private FirmaPanel firmaPanel;

    public FirmaController(FirmaList firmaList, FirmaPanel firmaPanel) {
        this.firmaList = firmaList;
        this.firmaPanel = firmaPanel;
    }

    /**
     * Updated das TableModel
     * @Author Justin
     */
    public void loadFirma() {
        this.firmaList.getFirmen().forEach(row -> {
            this.firmaPanel.getFirmaTableModel().addRow(new Object[]{
                    row.getFirmenID(),
                    row.getName(),
                    row.getAnzahlWuensche(),
                    row.getMaximaleAnzahlSchueler()
            });
        });
    }
}
