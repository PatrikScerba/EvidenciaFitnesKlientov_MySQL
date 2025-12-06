package sk.patrikscerba.ui;

import sk.patrikscerba.db.KlientDaoImpl;
import sk.patrikscerba.model.Klient;
import sk.patrikscerba.utils.ValidaciaVstupov;
import javax.swing.*;
import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

// Trieda Vyhľadávanie umožňuje vyhľadávať klientov podľa mena a priezviska
public class Vyhladavanie extends JFrame {
    private JPanel mainPanel;
    private JTextField jTextKrstneMenoLabel;
    private JTextField jTextPriezvisko;
    private JButton buttonHladat;
    private JLabel priezviskoLabel;
    private JLabel krstnéMenoLabel;

    private final boolean zobrazit;

    //Konštruktor triedy Vyhľadávanie-inicializácia okna a nastavenie možnosti hľadania klienta
    public Vyhladavanie(boolean zobrazit) {
        this.zobrazit = zobrazit;

        // Nastavenie základných vlastností okna
        setContentPane(mainPanel);
        setTitle("Vyhľadávanie klienta");
        setSize(500, 250);
        setLocationRelativeTo(null);
        mainPanel.setBackground(new Color(242,244,247));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Akcia tlačidla Hľadať
        buttonHladat.addActionListener(e -> {

            String inMeno = jTextKrstneMenoLabel.getText().trim();
            String inPriezvisko = jTextPriezvisko.getText().trim();

            // Normalizácia vstupov pre vyhľadávanie
            String hladaneMeno = ValidaciaVstupov.normalizujText(jTextKrstneMenoLabel.getText());
            String hladanePriezvisko = ValidaciaVstupov.normalizujText(jTextPriezvisko.getText());

            if (hladaneMeno.isEmpty() && hladanePriezvisko.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Zadaj aspoň meno alebo priezvisko.",
                        "Vyhľadávanie", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Načítanie všetkých klientov z databázy
            List<Klient> klienti;

            try {
                KlientDaoImpl dao = new KlientDaoImpl();
                klienti = dao.nacitajVsetkychKlientov();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Nepodarilo sa načítať klientov z databázy:\n" + ex.getMessage(),
                        "Chyba databázy",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Klient> zhodniKlienti = new ArrayList<>();

            // Vyhľadávanie cez normalizované meno/priezvisko (bez diakritiky)
            for (Klient k : klienti) {
                String meno = normalize(k.getKrstneMeno());
                String priezvisko = normalize(k.getPriezvisko());

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
                new DetailKlienta(zhodniKlienti.get(0), zobrazit).setVisible(true);
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
                            new DetailKlienta(k,zobrazit).setVisible(true);
                            break;
                        }
                    }
                }
            }
        });
    }

    // Pomocná metóda na normalizáciu textu (odstránenie diakritiky, malá písmená, trim)
    private String normalize(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
    }
}
