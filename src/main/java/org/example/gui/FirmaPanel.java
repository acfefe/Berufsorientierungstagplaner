package org.example.gui;

import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Data
public class FirmaPanel extends JPanel {

    private final DefaultTableModel firmaTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "AnzahlWünsche", "maximaleAnzahl"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable firmaTable = new JTable(firmaTableModel);
    private final JScrollPane firmaTableScrollPane = new JScrollPane(firmaTable);

    public FirmaPanel() {
        initialize();
    }

    /**
     * Baut die GUI auf
     * @Author Justin
     */
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.add(this.firmaTableScrollPane, BorderLayout.CENTER);

        this.firmaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(this.getClass().getSimpleName());

        this.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(titleLabel);
    }
}
