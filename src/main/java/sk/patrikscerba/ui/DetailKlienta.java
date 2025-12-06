package sk.patrikscerba.ui;

import sk.patrikscerba.model.Klient;
import sk.patrikscerba.db.KlientDaoImpl;
import sk.patrikscerba.utils.ValidaciaVstupov;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Okno na zobrazenie detailov klienta s možnosťou úprav a vymazania.
public class DetailKlienta extends JFrame {

    private Klient klient;
    private JPanel mainPanel;
    private JLabel labKrstneMeno;
    private JLabel labPriezvisko;
    private JLabel labVek;
    private JLabel labEmail;
    private JLabel labAdresa;
    private JLabel labTelefonneCislo;
    private JLabel labDatumNarodenia;
    private JLabel labelDatumRegistracie;

    private JButton vymazatButton;
    private JButton upravitButton;
    private JButton zatvoritButton;
    private JButton zrusitUpravyButton;

    private JTextField editKrstneMeno;
    private JTextField editPriezvisko;
    private JTextField editEmail;
    private JTextField editAdresa;
    private JTextField editTelefonneCislo;
    private JTextField editDatumNarodenia;

    private JLabel UpravKrstneMenoLabel;
    private JLabel UpravPriezviskoLabel;
    private JLabel UpravEmailLabel;
    private JLabel UpravAdresaLabel;
    private JLabel UpravTelefonneCisloLabel;
    private JLabel UpravDatumNarodeniaLabel;

    private boolean editMode = false;
    private final String povodneMeno;
    private final String povodnePriezvisko;

    // Formátovač dátumu
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Konštruktor triedy DetailKlienta
    public DetailKlienta(Klient klient, boolean zobrazitOkno) {
        this.klient = klient;
        this.povodneMeno = klient.getKrstneMeno();
        this.povodnePriezvisko = klient.getPriezvisko();

        // Nastavenie základných vlastností okna
        setContentPane(mainPanel);
        pack();
        mainPanel.setBackground(new Color(242,244,247));
        upravitButton.setText("✏️ Upraviť klienta");

        setTitle("Detail klienta - " + klient.getKrstneMeno() + " " + klient.getPriezvisko());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        zobrazUdaje();
        nastavViditelnostEditacnychPoli(false);
        zrusitUpravyButton.setVisible(false);

        // Nastavenie akcií tlačidiel
        zrusitUpravyButton.addActionListener(e -> zrusiUpravy());
        zatvoritButton.addActionListener(e -> dispose());
        vymazatButton.addActionListener(e -> vymazKlienta());
        upravitButton.addActionListener(e -> {
            if (!editMode) prepniNaRezimUprav();
            else ulozZmeny();
        });

        if (zobrazitOkno) setVisible(true);
    }

    // Zobrazí údaje klienta v okne
    private void zobrazUdaje() {
        int vypocitanyVek = ValidaciaVstupov.vypocitajVek(klient.getDatumNarodenia());
        labKrstneMeno.setText("Meno: " + klient.getKrstneMeno());
        labPriezvisko.setText("Priezvisko: " + klient.getPriezvisko());
        labVek.setText("Vek: " + vypocitanyVek);
        labVek.setText("Vek: " + klient.getVek());
        labEmail.setText("E-mail: " + klient.getEmail());
        labAdresa.setText("Adresa: " + klient.getAdresa());
        labTelefonneCislo.setText("Telefón: " + klient.getTelefonneCislo());
        labDatumNarodenia.setText("Dátum narodenia: " + klient.getDatumNarodeniaFormatted());
        labelDatumRegistracie.setText(
                "Dátum registrácie: " +
                                klient.getDatumRegistracie().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    // Nastaví viditeľnosť editačných polí
    private void nastavViditelnostEditacnychPoli(boolean viditelne) {
        editKrstneMeno.setVisible(viditelne);
        editPriezvisko.setVisible(viditelne);
        editEmail.setVisible(viditelne);
        editAdresa.setVisible(viditelne);
        editTelefonneCislo.setVisible(viditelne);
        editDatumNarodenia.setVisible(viditelne);

        UpravKrstneMenoLabel.setVisible(viditelne);
        UpravPriezviskoLabel.setVisible(viditelne);
        UpravEmailLabel.setVisible(viditelne);
        UpravAdresaLabel.setVisible(viditelne);
        UpravTelefonneCisloLabel.setVisible(viditelne);
        UpravDatumNarodeniaLabel.setVisible(viditelne);
    }

    // Prepne okno do editačného režimu (skryje texty, zobrazí políčka)
    private void prepniNaRezimUprav() {
        editMode = true;

        labKrstneMeno.setVisible(false);
        labPriezvisko.setVisible(false);
        labVek.setVisible(false);
        labEmail.setVisible(false);
        labAdresa.setVisible(false);
        labTelefonneCislo.setVisible(false);
        labDatumNarodenia.setVisible(false);

        zatvoritButton.setVisible(false);

        nastavViditelnostEditacnychPoli(true);

        editKrstneMeno.setText(klient.getKrstneMeno());
        editPriezvisko.setText(klient.getPriezvisko());
        editEmail.setText(klient.getEmail());
        editAdresa.setText(klient.getAdresa());
        editTelefonneCislo.setText(klient.getTelefonneCislo());

        LocalDate dn = klient.getDatumNarodenia();
        editDatumNarodenia.setText(dn != null ? dn.format(FORMATTER) : "");

        zrusitUpravyButton.setVisible(true);
        upravitButton.setText("💾 Uložiť zmeny");
        mainPanel.setBackground(new Color(232, 236, 240, 255));
    }
    // Uloží zmenené údaje klienta do databázy a aktualizuje UI
    private void ulozZmeny() {
        try {
            if (editKrstneMeno.getText().trim().isEmpty() || editPriezvisko.getText().trim().isEmpty()) {

                showWarn("Meno a priezvisko musia byť vyplnené!");
                return;
            }

            // Aktualizácia údajov klienta z editačných polí
            klient.setKrstneMeno(editKrstneMeno.getText().trim());
            klient.setPriezvisko(editPriezvisko.getText().trim());
            klient.setAdresa(editAdresa.getText().trim());
            klient.setEmail(editEmail.getText().trim());
            klient.setTelefonneCislo(editTelefonneCislo.getText().trim());

            //Validácia  dátumu narodenia
            String datumText = editDatumNarodenia.getText().trim();
            if (!datumText.isEmpty()) {
                klient.setDatumNarodenia(LocalDate.parse(datumText, FORMATTER));
            }

            // Uloženie zmien do databázy
            KlientDaoImpl dao = new KlientDaoImpl();
            dao.updateKlienta(klient);

            JOptionPane.showMessageDialog(this,
                    "Zmeny boli úspešne uložené.",
                        "Úprava klienta",
                            JOptionPane.INFORMATION_MESSAGE);

            // Obnovenie zobrazenia údajov po úpravách
            zobrazUdaje();

            labKrstneMeno.setVisible(true);
            labPriezvisko.setVisible(true);
            labVek.setVisible(true);
            labEmail.setVisible(true);
            labAdresa.setVisible(true);
            labTelefonneCislo.setVisible(true);
            labDatumNarodenia.setVisible(true);
            labelDatumRegistracie.setVisible(true);

            zrusitUpravyButton.setVisible(false);
            nastavViditelnostEditacnychPoli(false);

            zatvoritButton.setVisible(true);
            upravitButton.setText("✏️ Upraviť klienta");

            mainPanel.setBackground(new Color(242,244,247));
            editMode = false;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri ukladaní: " + ex.getMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Vymaže klienta z databázy po potvrdení užívateľa
    private void vymazKlienta() {
        int potvrdenie = JOptionPane.showConfirmDialog(this,
                "Naozaj chcete vymazať tohto klienta?",
                "Potvrdenie vymazania",
                JOptionPane.YES_NO_OPTION);

        if (potvrdenie == JOptionPane.YES_OPTION) {
            try {
                KlientDaoImpl dao = new KlientDaoImpl();
                dao.vymazatKlienta(klient);

                JOptionPane.showMessageDialog(this,
                        "Klient bol úspešne vymazaný.",
                        "Vymazanie klienta",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Chyba pri vymazaní klienta: " + ex.getMessage(),
                        "Chyba",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Zruší úpravy a vráti pôvodné údaje v UI
    private void zrusiUpravy(){
        editMode = false;
        nastavViditelnostEditacnychPoli(false);

        labKrstneMeno.setVisible(true);
        labPriezvisko.setVisible(true);
        labVek.setVisible(true);
        labEmail.setVisible(true);
        labAdresa.setVisible(true);
        labTelefonneCislo.setVisible(true);
        labDatumNarodenia.setVisible(true);
        labelDatumRegistracie.setVisible(true);

        upravitButton.setText("✏️ Upraviť klienta");
        zatvoritButton.setVisible(true);
        zrusitUpravyButton.setVisible(false);

        mainPanel.setBackground(new Color(242,244,247));

        zobrazUdaje();
    }

        // Pomocná metóda na zobrazenie varovania
        private void showWarn (String msg){
            JOptionPane.showMessageDialog(this, msg, "Upozornenie", JOptionPane.WARNING_MESSAGE);
        }
    }

