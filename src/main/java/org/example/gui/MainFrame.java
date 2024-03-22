package org.example.gui;

import lombok.Data;

import javax.swing.*;

@Data
public class MainFrame extends JFrame {
    private final SchuelerPanel schuelerPanel;
    private final FirmaPanel firmaPanel;
    private final RaumPanel raumPanel;

    private final JPanel buttonPanel = new JPanel();

    private final JMenuItem viewStart = new JMenuItem("Start");
    private final JMenuItem viewSchueler = new JMenuItem("Schüler");
    private final JMenuItem viewFirma = new JMenuItem("Firma");
    private final JMenuItem viewRaum = new JMenuItem("Raum");
    private final JMenuItem loadSchueler = new JMenuItem("Schüler");
    private final JMenuItem loadFirma = new JMenuItem("Firma");
    private final JMenuItem loadRaum = new JMenuItem("Raum");


    private final JButton startBtn = new JButton("Start Algorithm");

    public MainFrame(SchuelerPanel schuelerPanel, FirmaPanel firmaPanel, RaumPanel raumPanel) {
        this.schuelerPanel = schuelerPanel;
        this.firmaPanel = firmaPanel;
        this.raumPanel = raumPanel;
        initialize();
        viewSchueler();
        viewFirma();
        viewRaum();
        viewStart();
    }

    /**
     * Baut die GUI auf
     * @Author Justin
     */
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
        loadDataMenu.add(this.loadRaum);

        viewMenu.add(this.viewStart);
        viewMenu.add(this.viewSchueler);
        viewMenu.add(this.viewFirma);
        viewMenu.add(this.viewRaum);


        this.buttonPanel.add(this.startBtn);

        this.setContentPane(this.buttonPanel);

        this.setVisible(true);
    }

    /**
     * Wechselt auf Knopfdruck das ContentPane auf das SchülerPanel
     * @Author Justin
     */
    public void viewSchueler() {
        this.viewSchueler.addActionListener(e -> {
            this.setContentPane(this.schuelerPanel);
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * Wechselt auf Knopfdruck das ContentPane auf das FirmaPanel
     * @Author Justin
     */
    public void viewFirma() {
        this.viewFirma.addActionListener(e -> {
            this.setContentPane(this.firmaPanel);
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * Wechselt auf Knopfdruck das ContentPane auf das RaumPanel
     * @Author Justin
     */
    public void viewRaum() {
        this.viewRaum.addActionListener(e -> {
            this.setContentPane(this.raumPanel);
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * Wechselt auf Knopfdruck das ContentPane auf das ursprüngliche Panel
     * @Author Justin
     */
    public void viewStart() {
        this.viewStart.addActionListener(e -> {
            this.setContentPane(this.buttonPanel);
            this.revalidate();
            this.repaint();
        });
    }
}
