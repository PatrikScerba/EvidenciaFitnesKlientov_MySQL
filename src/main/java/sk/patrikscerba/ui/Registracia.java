package sk.patrikscerba.ui;

import sk.patrikscerba.data.ValidaciaVstupov;
import sk.patrikscerba.model.Klient;
import sk.patrikscerba.data.XMLZapisKlientov;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Okno pre registráciu nového klienta.
 */
public class Registracia extends JFrame {

    private JPanel mainPanel;
    private JTextField jTextKrstneMeno;
    private JTextField jTextPriezvisko;
    private JTextField jTextVek;
    private JTextField jTextEmail;
    private JTextField jTextTelefonneCislo;
    private JTextField jTextDatumNarodenia;
    private JButton buttonRegistrovat;
    private JLabel vekLabel;
    private JLabel registraciaKlientaLabel;
    private JLabel priezviskoLabel;
    private JLabel emaillLabel;
    private JLabel telefonneCisloLabel;
    private JLabel datumNarodeniaLabel;

    // Formátovač dátumu
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //Konštruktor triedy Registracia
    public Registracia() {

        setContentPane(mainPanel);
        setTitle("Registrácia klienta");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        buttonRegistrovat.addActionListener(e -> registrujKlienta());
    }

    //Metóda na registráciu klienta
    private void registrujKlienta() {
        try {
            String krstneMeno = jTextKrstneMeno.getText().trim();
            String priezvisko = jTextPriezvisko.getText().trim();
            String vekText = jTextVek.getText().trim();
            String email = jTextEmail.getText().trim();
            String telefonneCislo = jTextTelefonneCislo.getText().trim();
            String datumNarodeniaText = jTextDatumNarodenia.getText().trim();

            //Validácia vstupov pri registrácii
            if (!ValidaciaVstupov.obsahujeLenPismena(krstneMeno)) {
                showWarning("Zadajte platné krstné meno!");
                return;
            }

            if (!ValidaciaVstupov.obsahujeLenPismena(priezvisko)) {
                showWarning("Zadajte platné priezvisko!");
                return;
            }

            if (!ValidaciaVstupov.jePlatnyVek(vekText)) {
                showWarning("Zadajte platný vek (číslo od 0 do 120)!");
                return;
            }

            int vek = Integer.parseInt(vekText);

            if (!ValidaciaVstupov.jePlatnyEmail(email)) {
                showWarning("Neplatný formát e-mailovej adresy!");
                return;
            }

            if (!ValidaciaVstupov.jePlatnyTelefon(telefonneCislo)) {
                showWarning("Neplatný formát telefónneho čísla!");
                return;
            }

            if (!ValidaciaVstupov.jePlatnyDatum(datumNarodeniaText)) {
                showWarning("Zadajte dátum vo formáte dd.MM.yyyy (napr. 10.10.1999)!");
                return;
            }
            // Parsovanie dátumu narodenia
            LocalDate datumNarodenia = LocalDate.parse(datumNarodeniaText, FORMATTER);

            //Vytvorenie a uloženie nového klienta
            Klient novyKlient = new Klient(
                    krstneMeno,
                    priezvisko,
                    vek,
                    email,
                    telefonneCislo,
                    datumNarodenia
            );
            //Zapis klienta do XML súboru
            XMLZapisKlientov zapis = new XMLZapisKlientov();
            zapis.ulozKlienta(novyKlient);

            JOptionPane.showMessageDialog(this,
                    "Klient bol úspešne zaregistrovaný.",
                    "Registrácia úspešná",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri registrácii klienta: " + ex.getMessage(),
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //Pomocná metóda na zobrazenie varovania
    private void showWarning(String text) {
        JOptionPane.showMessageDialog(this, text, "Upozornenie", JOptionPane.WARNING_MESSAGE);
    }

}

