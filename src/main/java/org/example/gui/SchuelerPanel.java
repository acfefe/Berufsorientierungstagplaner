package org.example.gui;

import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Data
public class SchuelerPanel extends JPanel {

    private final DefaultTableModel schuelerTableModel = new DefaultTableModel(new Object[]{"Klasse", "Nachname", "Vorname", "Wahlen"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable schuelerTable = new JTable(schuelerTableModel);
    private final JScrollPane schuelerTableScrollPane = new JScrollPane(schuelerTable);

    public SchuelerPanel() {
        initialize();
    }

    public void initialize() {
        this.setLayout(new BorderLayout());
        this.add(this.schuelerTableScrollPane, BorderLayout.CENTER);

        this.schuelerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(this.getClass().getSimpleName());

        this.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(titleLabel);
    }
}
