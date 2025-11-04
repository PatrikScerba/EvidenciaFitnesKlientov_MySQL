package sk.patrikscerba.ui;

import sk.patrikscerba.model.Klient;
import sk.patrikscerba.data.XMLZapisKlientov;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Okno na zobrazenie detailov klienta s možnosťou úprav a vymazania.
 */
public class DetailKlienta extends JFrame {

    private Klient klient;
    private JPanel mainPanel;
    private JLabel labKrstneMeno;
    private JLabel labPriezvisko;
    private JLabel labVek;
    private JLabel labEmail;
    private JLabel labTelefonneCislo;
    private JLabel labDatumNarodenia;
    private JButton vymazatButton;
    private JButton upravitButton;
    private JButton zatvoritButton;
    private JTextField editKrstneMeno;
    private JTextField editPriezvisko;
    private JTextField editVek;
    private JTextField editEmail;
    private JTextField editTelefonneCislo;
    private JTextField editDatumNarodenia;

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
        setTitle("Detail klienta - " + klient.getKrstneMeno() + " " + klient.getPriezvisko());
        setSize(620, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        zobrazUdaje();
        nastavViditelnostEditacnychPoli(false);

        zatvoritButton.addActionListener(e -> dispose());
        vymazatButton.addActionListener(e -> vymazKlienta());
        upravitButton.addActionListener(e -> { if (!editMode) prepniNaRezimUprav(); else ulozZmeny(); });

        if (zobrazitOkno) setVisible(true);
    }
    // Metóda na zobrazenie údajov klienta v štítkoch
    private void zobrazUdaje() {
        labKrstneMeno.setText("Meno: " + klient.getKrstneMeno());
        labPriezvisko.setText("Priezvisko: " + klient.getPriezvisko());
        labVek.setText("Vek: " + klient.getVek());
        labEmail.setText("Email: " + klient.getEmail());
        labTelefonneCislo.setText("Telefón: " + klient.getTelefonneCislo());
        labDatumNarodenia.setText("Dátum narodenia: " + klient.getDatumNarodeniaFormatted());
    }
    // Metóda na nastavenie viditeľnosti editačných polí pre úpravy
    private void nastavViditelnostEditacnychPoli(boolean viditelne) {
        editKrstneMeno.setVisible(viditelne);
        editPriezvisko.setVisible(viditelne);
        editVek.setVisible(viditelne);
        editEmail.setVisible(viditelne);
        editTelefonneCislo.setVisible(viditelne);
        editDatumNarodenia.setVisible(viditelne);
    }
    // Metóda na prepnutie do režimu úprav
    // Skrytie štítkov a zobrazenie editačných polí
    private void prepniNaRezimUprav() {
        editMode = true;

        labKrstneMeno.setVisible(false);
        labPriezvisko.setVisible(false);
        labVek.setVisible(false);
        labEmail.setVisible(false);
        labTelefonneCislo.setVisible(false);
        labDatumNarodenia.setVisible(false);

        nastavViditelnostEditacnychPoli(true);

        editKrstneMeno.setText(klient.getKrstneMeno());
        editPriezvisko.setText(klient.getPriezvisko());
        editVek.setText(String.valueOf(klient.getVek()));
        editEmail.setText(klient.getEmail());
        editTelefonneCislo.setText(klient.getTelefonneCislo());

        LocalDate dn = klient.getDatumNarodenia();
        editDatumNarodenia.setText(dn != null ? dn.format(FORMATTER) : "");

        upravitButton.setText("💾 Uložiť zmeny");
        mainPanel.setBackground(new Color(234, 232, 232));
    }
    // Metóda na uloženie zmien po úpravách
    // Vezme hodnoty z editačných polí, validuje ich a uloží zmeny do XML a obnoví zobrazenie
    private void ulozZmeny() {
        try {
            if (editKrstneMeno.getText().trim().isEmpty() || editPriezvisko.getText().trim().isEmpty()) {
                showWarn("Meno a priezvisko musia byť vyplnené!");
                return;
            }
            //
            int vek;
            try {
                vek = Integer.parseInt(editVek.getText().trim());
            } catch (NumberFormatException ex) {
                showWarn("Vek musí byť číslo!");
                return;
            }
            // Validácia veku
            klient.setKrstneMeno(editKrstneMeno.getText().trim());
            klient.setPriezvisko(editPriezvisko.getText().trim());
            klient.setVek(vek);
            klient.setEmail(editEmail.getText().trim());
            klient.setTelefonneCislo(editTelefonneCislo.getText().trim());

            //Validácia  dátumu narodenia
            String datumText = editDatumNarodenia.getText().trim();
            if (!datumText.isEmpty()) {
                klient.setDatumNarodenia(LocalDate.parse(datumText, FORMATTER));
            }
            // Uloženie zmien do XML
            new XMLZapisKlientov().aktualizujKlienta(klient, povodneMeno, povodnePriezvisko);

            JOptionPane.showMessageDialog(this, "Zmeny boli úspešne uložené.",
                    "Úprava klienta", JOptionPane.INFORMATION_MESSAGE);

            // Obnovenie zobrazenia údajov po úpravách
            zobrazUdaje();
            labKrstneMeno.setVisible(true);
            labPriezvisko.setVisible(true);
            labVek.setVisible(true);
            labEmail.setVisible(true);
            labTelefonneCislo.setVisible(true);
            labDatumNarodenia.setVisible(true);

            nastavViditelnostEditacnychPoli(false);
            upravitButton.setText("✏️ Upraviť klienta");
            mainPanel.setBackground(Color.WHITE);
            editMode = false;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri ukladaní: " + ex.getMessage(),
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Metóda na vymazanie klienta
    private void vymazKlienta() {
        int potvrdenie = JOptionPane.showConfirmDialog(this,
                "Naozaj chcete vymazať tohto klienta?",
                "Potvrdenie vymazania",
                JOptionPane.YES_NO_OPTION);

        if (potvrdenie == JOptionPane.YES_OPTION) {
            new XMLZapisKlientov().vymazatKlienta(klient);
            JOptionPane.showMessageDialog(this,
                    "Klient bol úspešne vymazaný.",
                    "Vymazanie klienta",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
    // Pomocná metóda na zobrazenie varovania
    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Upozornenie", JOptionPane.WARNING_MESSAGE);
    }
}
