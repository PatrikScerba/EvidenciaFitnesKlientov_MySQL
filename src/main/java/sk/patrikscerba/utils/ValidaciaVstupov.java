package sk.patrikscerba.utils;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Trieda na validíciu vstupov od používateľa
public class ValidaciaVstupov {

    //Formát dátumu podľa (dd.MM.yyyy)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //Normalizuje text (odstráni diakritiku, premení na malé písmená)
    public static String normalizujText(String text) {
        if (text == null) return "";
        return Normalizer.normalize(text.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
    //Overí, či je text prázdny alebo null
    public static boolean jePrazdne(String text) {
        return text == null || text.trim().isEmpty();
    }
    //Overí, či text obsahuje len písmená (vrátane slovenských znakov)
    public static boolean obsahujeLenPismena(String text) {
        if (jePrazdne(text)) return false;
        return text.matches("[\\p{L}]+");
    }
    //Overí platnosť e-mailovej adresy
    public static boolean jePlatnyEmail(String email) {
        if (jePrazdne(email)) return false;
        return email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    //Overí platnosť telefónneho čísla (povolí +421 a medzery)
    public static boolean jePlatnyTelefon(String telefon) {
        if (jePrazdne(telefon)) return false;
        return telefon.matches("^\\+?\\d(?:[\\d\\s-]{8,15})$");
    }
    //Vypočíta vek na základe dátumu narodenia
    public static int vypocitajVek(LocalDate datumNarodenia) {
        int vek = LocalDate.now().getYear()-datumNarodenia.getYear();
        if (datumNarodenia.plusYears(vek).isAfter(LocalDate.now())) {
            vek--;
        }
        return vek;
    }
    //Overerenie platnosti dátumu vo formáte dd.MM.yyyy
    public static boolean jePlatnyDatum(String datumText) {
        if (jePrazdne(datumText)) return false;
        try {
            LocalDate.parse(datumText.trim(), FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    // Prečíta dátum a ak je neplatný, vráti aktuálny dátum
    public static LocalDate nacitajDatumBezChyby(String datumText) {
        try {
            return LocalDate.parse(datumText, FORMATTER);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
