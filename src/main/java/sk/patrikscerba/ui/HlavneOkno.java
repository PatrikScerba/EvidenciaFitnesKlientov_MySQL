package sk.patrikscerba.ui;

import javax.swing.*;

// Hlavné okno aplikácie pre evidenciu klientov
public class HlavneOkno extends JFrame {
    public static void main(String[] args) {
    }
    //Hlavné okno aplikácie a jej komponenty
    private JPanel mainPanel;
    private JButton Registracia;
    private JButton Vyhladanie;
    private JButton Klienti;
    private JLabel verziaLabel;

    //Konštruktor hlavného okna
    public HlavneOkno() {

        setContentPane(mainPanel);
        setTitle("Evidencia klientov");
        setSize(650, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //Nastavenie akcií tlačidiel v hlavnom okne
        Registracia.addActionListener(e -> new Registracia().setVisible(true));
        Vyhladanie.addActionListener(e -> new Vyhladavanie(false).setVisible(true) );
        Klienti.addActionListener(e -> new ZoznamKlientov().setVisible(true) );
    }
}



