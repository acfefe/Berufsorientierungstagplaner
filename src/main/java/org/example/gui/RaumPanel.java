package org.example.gui;

import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Data
public class RaumPanel extends JPanel {

    private final DefaultTableModel raumTableModel = new DefaultTableModel(new Object[]{"name"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable raumTable = new JTable(raumTableModel);
    private final JScrollPane raumTableScrollPane = new JScrollPane(raumTable);

    public RaumPanel() {
        initialize();
    }

    /**
     * Baut die GUI auf
     * @Author Justin
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.add(this.raumTableScrollPane, BorderLayout.CENTER);

        this.raumTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(this.getClass().getSimpleName());

        this.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(titleLabel);
    }
}
