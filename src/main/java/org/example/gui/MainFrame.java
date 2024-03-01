package org.example.gui;

import lombok.Data;

import javax.swing.*;

@Data
public class MainFrame extends JFrame {

    private final SchuelerPanel schuelerPanel;
    private final FirmaPanel firmaPanel;

    private final JMenuItem viewSchueler = new JMenuItem("Schüler");
    private final JMenuItem viewFirma = new JMenuItem("Firma");
    private final JMenuItem loadSchueler = new JMenuItem("Schüler");

    public MainFrame(SchuelerPanel schuelerPanel, FirmaPanel firmaPanel) {
        this.schuelerPanel = schuelerPanel;
        this.firmaPanel = firmaPanel;
        initialize();
        viewSchueler();
        viewFirma();
    }

    public void initialize() {
        this.setTitle("Berufsorientierungstagplaner");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");

        JMenu loadDataMenu = new JMenu("Open");

        this.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);

        fileMenu.add(loadDataMenu);
        loadDataMenu.add(this.loadSchueler);

        viewMenu.add(this.viewSchueler);
        viewMenu.add(this.viewFirma);

        this.setVisible(true);
    }

    public void viewSchueler() {
        this.viewSchueler.addActionListener(e -> {
            this.setContentPane(schuelerPanel);
            this.revalidate();
            this.repaint();
        });
    }

    public void viewFirma() {
        this.viewFirma.addActionListener(e -> {
            this.setContentPane(firmaPanel);
            this.revalidate();
            this.repaint();
        });
    }
}
