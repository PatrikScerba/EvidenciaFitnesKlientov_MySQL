package sk.patrikscerba.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Klient {

    private int id;
    private String krstneMeno;
    private String priezvisko;
    private LocalDate datumNarodenia;
    private String telefonneCislo;
    private String adresa;
    private String email;
    private LocalDate datumRegistracie;

    // Formatovanie dátumu na dd.MM.yyyy aby sa zobrazoval správne v UI
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //Konštruktor triedy Klient používaný pri načítaní klienta z databázy
    public Klient(int id, String krstneMeno, String priezvisko, LocalDate datumNarodenia,
                  String telefonneCislo, String adresa, String email, LocalDate datumRegistracie) {
        this.id = id;
        this.krstneMeno = krstneMeno;
        this.priezvisko = priezvisko;
        this.datumNarodenia = datumNarodenia;
        this.telefonneCislo = telefonneCislo;
        this.adresa = adresa;
        this.email = email;
        this.datumRegistracie = datumRegistracie;
    }
    //Konštruktor triedy Klient používaný pri registrácii nového klienta
    public Klient(String krstneMeno, String priezvisko, String email, String telefonneCislo, LocalDate datumNarodenia) {
        this.krstneMeno = krstneMeno;
        this.priezvisko = priezvisko;
        this.email = email;
        this.telefonneCislo = telefonneCislo;
        this.datumNarodenia = datumNarodenia;
    }
    //Gettery a settery pre atribúty triedy Klient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKrstneMeno() {
        return krstneMeno;
    }

    public void setKrstneMeno(String krstneMeno) {
        this.krstneMeno = krstneMeno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public LocalDate getDatumNarodenia() {
        return datumNarodenia;
    }

    public void setDatumNarodenia(LocalDate datumNarodenia) {
        this.datumNarodenia = datumNarodenia;
    }

    public String getTelefonneCislo() {
        return telefonneCislo;
    }

    public void setTelefonneCislo(String telefonneCislo) {
        this.telefonneCislo = telefonneCislo;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDatumRegistracie() {
        return datumRegistracie;
    }

    public void setDatumRegistracie(LocalDate datumRegistracie) {
        this.datumRegistracie = datumRegistracie;
    }

    //Vypočíta sa vek a vráti vypočítaný vek klienta na základe dátumu narodenia
    public int getVek() {
        if (datumNarodenia == null) {
            return 0;
        }
        return LocalDate.now().getYear() - datumNarodenia.getYear();
    }
    //Setter pre vek nechávame prázdny, pretože vek sa počíta z dátumu narodenia
    public void setVek(int vek) {

    }
    // Vráti naformátovaný dátum narodenia ako reťazec vo formáte dd.MM.yyyy
    public String getDatumNarodeniaFormatted() {
        if (datumNarodenia == null) {
            return "";
        }
        return datumNarodenia.format(FORMATTER);
    }
    // Nastaví dátum registrácie z naformátovaného reťazca
    public void setDatumRegistracieFormatted(String datumText) {
        if (datumRegistracie == null) {
            return;
        }
        this.datumRegistracie = LocalDate.parse(datumText, FORMATTER);
    }
}
