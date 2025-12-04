package sk.patrikscerba.ui;

import sk.patrikscerba.db.KlientDaoImpl;
import sk.patrikscerba.utils.ValidaciaVstupov;
import sk.patrikscerba.model.Klient;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Okno pre registráciu nového klienta.
 */
public class Registracia extends JFrame {

    private JPanel mainPanel;
    private JButton buttonRegistrovat;
    private JTextField jTextKrstneMeno;
    private JTextField jTextPriezvisko;
    private JTextField jTextDatumNarodenia;
    private JTextField jTextTelefonneCislo;
    private JTextField jTextAdresa;
    private JTextField jTextEmail;

    private JLabel krstneMenoLabel;
    private JLabel emailLabel;
    private JLabel telefonneCisloLabel;
    private JLabel datumNarodeniaLabel;
    private JLabel adresaLabel;
    private JLabel registraciaKlientaLabel;
    private JLabel priezviskoLabel;


    // Formátovač dátumu
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //Konštruktor triedy Registrácia a nastavenie okna
    public Registracia() {

        setContentPane(mainPanel);
        setTitle("Registrácia klienta");
        setSize(450, 600);
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
            String email = jTextEmail.getText().trim();
            String adresa = jTextAdresa.getText().trim();
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

            // Overenie veku klienta
            int vek = ValidaciaVstupov.vypocitajVek(datumNarodenia);
            if (vek < 15) {
                showWarning("Klient musí mať minimálne 15 rokov!");
                return;
            }
            // Vytvorenie nového klienta
            Klient novyKlient = new Klient(krstneMeno, priezvisko, email, telefonneCislo, datumNarodenia);
            novyKlient.setAdresa(adresa);

            // Uloženie klienta do databázy
            KlientDaoImpl klientDao = new KlientDaoImpl();

            String[] stlpce = {"krstne_meno", "priezvisko", "datum_narodenia", "telefonne_cislo", "email", "adresa"};
            Object[] hodnoty = {
                    novyKlient.getKrstneMeno(),
                    novyKlient.getPriezvisko(),
                    novyKlient.getDatumNarodenia(),
                    novyKlient.getTelefonneCislo(),
                    novyKlient.getEmail(),
                    novyKlient.getAdresa(),
            };
            klientDao.insert("klienti", stlpce, hodnoty);

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





