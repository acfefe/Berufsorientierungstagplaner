package org.example.gui;

import lombok.Data;

import javax.swing.*;

@Data
public class MainFrame extends JFrame {
    private final SchuelerPanel schuelerPanel;
    private final FirmaPanel firmaPanel;
    private final RaumPanel raumPanel;

    private final JMenuItem viewSchueler = new JMenuItem("Schüler");
    private final JMenuItem viewFirma = new JMenuItem("Firma");
    private final JMenuItem loadSchueler = new JMenuItem("Schüler");
    private final JMenuItem loadFirma = new JMenuItem("Firma");
    private final JMenuItem viewRaum = new JMenuItem("Raum");

    private final JButton startBtn = new JButton("Start Algorithm");

    public MainFrame(SchuelerPanel schuelerPanel, FirmaPanel firmaPanel, RaumPanel raumPanel) {
        this.schuelerPanel = schuelerPanel;
        this.firmaPanel = firmaPanel;
        this.raumPanel = raumPanel;
        initialize();
        viewSchueler();
        viewFirma();
        viewRaum();
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
        loadDataMenu.add(this.loadFirma);

        viewMenu.add(this.viewSchueler);
        viewMenu.add(this.viewFirma);
        viewMenu.add(this.viewRaum);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.startBtn);

        this.setContentPane(buttonPanel);

        this.setVisible(true);
    }

    public void viewSchueler() {
        this.viewSchueler.addActionListener(e -> {
            this.setContentPane(this.schuelerPanel);
            this.revalidate();
            this.repaint();
        });
    }

    public void viewFirma() {
        this.viewFirma.addActionListener(e -> {
            this.setContentPane(this.firmaPanel);
            this.revalidate();
            this.repaint();
        });
    }

    public void viewRaum() {
        this.viewRaum.addActionListener(e -> {
            this.setContentPane(this.raumPanel);
            this.revalidate();
            this.repaint();
        });
    }
}
