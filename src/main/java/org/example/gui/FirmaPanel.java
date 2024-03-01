package org.example.gui;

import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Data
public class FirmaPanel extends JPanel {

    private final DefaultTableModel firmaTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "AnzahlWünsche", "maximaleAnzahl"}, 0);
    private final JTable firmaTable = new JTable(firmaTableModel);
    private final JScrollPane firmaTableScrollPane = new JScrollPane(firmaTable);

    public FirmaPanel() {
        initialize();
    }

    public void initialize() {
        this.setLayout(new BorderLayout());
        this.add(this.firmaTableScrollPane, BorderLayout.CENTER);
    }
}
