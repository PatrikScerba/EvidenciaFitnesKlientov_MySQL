package sk.patrikscerba.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// Trieda reprezentujúca hlavné okno aplikácie s tlačidlami na registráciu, vyhľadávanie a zobrazenie zoznamu klientov.
public class HlavneOkno extends JFrame {

    //Hlavné okno aplikácie a jeho komponenty
    private JPanel mainPanel;
    private JButton Registracia;
    private JButton Vyhladanie;
    private JButton Klienti;
    private JLabel verzia2Label;
    private JLabel developerByPatrikŠčerbaLabel;

    //Konštruktor hlavného okna a jeho nastavenie.
    public HlavneOkno() {

        setContentPane(mainPanel);
        setTitle("Evidencia klientov");
        setSize(650, 250);
        mainPanel.setBackground(new Color(242,244,247));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //Nastavenie akcií tlačidiel v hlavnom okne a ošetrenie databázových chýb pri načítaní klientov.
        Registracia.addActionListener(e -> new Registracia().setVisible(true));
        Vyhladanie.addActionListener(e -> new Vyhladavanie(false).setVisible(true) );
        Klienti.addActionListener(e -> {
            try {
                new ZoznamKlientov().setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nepodarilo sa načítať klientov z databázy."
                        , "Skontrolujte pripojenie k databáze",
                        JOptionPane.ERROR_MESSAGE
                );
                throw new RuntimeException(ex);
            }
        });
    }
}




