package sk.patrikscerba.ui;

import sk.patrikscerba.data.ValidaciaVstupov;
import sk.patrikscerba.model.Klient;
import sk.patrikscerba.data.XMLNacitanieKlientov;

import javax.swing.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

// Trieda Vyhľadávanie umožňuje vyhľadávať klientov podľa mena a priezviska
public class Vyhladavanie extends JFrame {
    private JPanel mainPanel;
    private JTextField jTextKrstneMeno;
    private JTextField jTextPriezvisko;
    private JButton buttonHladat;
    private JLabel priezviskoLabel;
    private JLabel krstnéMenoLabel;

    private final boolean vymazat;

    //Konštruktor triedy Vyhľadávanie a nastavenie akcií tlačidiel
    public Vyhladavanie(boolean vymazat) {
        this.vymazat = vymazat;

        //Nastavenie základných vlastností okna
        setContentPane(mainPanel);
        setTitle(vymazat ? "Vyhľadanie klienta na vymazanie" : "Vyhľadávanie klienta");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //Akcia tlačidla Hľadať a spracovanie vyhľadávania klientov podľa zadaných kritérií.
        buttonHladat.addActionListener(e -> {
            String inMeno = jTextKrstneMeno.getText().trim();
            String inPriezvisko = jTextPriezvisko.getText().trim();

            //Normalizácia vstupov (pre diakritiku, veľké/malé písmená, medzery)
            String hladaneMeno = ValidaciaVstupov.normalizujText(jTextKrstneMeno.getText());
            String hladanePriezvisko = ValidaciaVstupov.normalizujText(jTextPriezvisko.getText());

            //Overenie, či bolo vyplnené aspoň jedno pole a zobrazenie správy.
            if (hladaneMeno.isEmpty() && hladanePriezvisko.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Zadaj aspoň meno alebo priezvisko.",
                        "Vyhľadávanie", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //Načítanie klientov z XML a vyhľadávanie zhodných klientov
            List<Klient> klienti = new XMLNacitanieKlientov().nacitajKlientov();
            List<Klient> zhodniKlienti = new ArrayList<>();

            //Vyhľadávanie klientov podľa zadaných kritérií cez normalizované hodnoty
            for (Klient k : klienti) {
                String meno = normalize(k.getKrstneMeno());
                String priezvisko = normalize(k.getPriezvisko());

                //kontrola zhody podľa zadaných kritérií
                boolean zhoda = false;

                //Vyhľadávanie podľa mena, priezviska alebo oboch
                if (!hladaneMeno.isEmpty() && hladanePriezvisko.isEmpty()) {
                    zhoda = meno.equals(hladaneMeno) || meno.contains(hladaneMeno);
                }
                else if (hladaneMeno.isEmpty() && !hladanePriezvisko.isEmpty()) {
                    zhoda = priezvisko.equals(hladanePriezvisko) || priezvisko.contains(hladanePriezvisko);
                }
                else {
                    boolean menoZhoda = meno.equals(hladaneMeno) || meno.contains(hladaneMeno);
                    boolean priezviskoZhoda = priezvisko.equals(hladanePriezvisko) || priezvisko.contains(hladanePriezvisko);
                    zhoda = menoZhoda && priezviskoZhoda;
                }
                // Ak je zhoda, pridaj klienta do zoznamu zhodných klientov
                if (zhoda) zhodniKlienti.add(k);
            }
            // Spracovanie výsledkov vyhľadávania a zobrazenie detailov klienta alebo výberu z viacerých nájdených klientov
            if (zhodniKlienti.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Klient nebol nájdený.",
                        "Výsledok vyhľadávania",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (zhodniKlienti.size() == 1) {
                new DetailKlienta(zhodniKlienti.get(0), vymazat).setVisible(true);
            } else {
                String[] moznosti = zhodniKlienti.stream()
                        .map(k -> k.getKrstneMeno() + " " + k.getPriezvisko() + ", vek: " + k.getVek())
                        .toArray(String[]::new);

                // Dialóg na výber jedného z viacerých nájdených klientov
                String vybratKlienta = (String) JOptionPane.showInputDialog(
                        this,
                        "Nájdených viac klientov (" + zhodniKlienti.size() + "). Vyber jedného:",
                        "Výber klienta",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        moznosti,
                        moznosti[0]);

                // spracovanie výberu a zobrazenie detailov vybraného klienta
                if (vybratKlienta != null) {
                    for (Klient k : zhodniKlienti) {
                        String label = k.getKrstneMeno() + " " + k.getPriezvisko() + ", vek: " + k.getVek();
                        if (label.equals(vybratKlienta)) {
                            new DetailKlienta(k, vymazat).setVisible(true);
                            break;
                        }
                    }
                }
            }
        });
    }
    // Odstráni diakritiku, prevedie na malé písmená a oreže medzery
    // Používa sa pre presné porovnanie vstupov pri vyhľadávaní
    private String normalize(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}
