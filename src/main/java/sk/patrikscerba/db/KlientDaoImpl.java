package sk.patrikscerba.db;

import sk.patrikscerba.model.Klient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Trieda KlientDaoImpl implementuje základné CRUD operácie pre tabuľku klienti
public class KlientDaoImpl {

    // Pripojenie k databáze
    private Connection connection;

    //konštruktor, ktorý získa pripojenie k databáze cez DatabazaPripojenie
    public KlientDaoImpl() throws SQLException {
        this.connection = DatabazaPripojenie.getConnection();
    }
    // Pomocná metóda na vykonanie SQL dotazu s parametrami
    protected int vykonajDotaz(String sqlDotaz, Object[] parametre) throws SQLException {
        try (PreparedStatement pripravenyDotaz = connection.prepareStatement(sqlDotaz)) {
            if (parametre != null) {
                for (int i = 0; i < parametre.length; i++) {
                    pripravenyDotaz.setObject(i + 1, parametre[i]);
                }
            }
            return pripravenyDotaz.executeUpdate();
        }
    }
    // Metóda na vymazanie záznamu z tabuľky + podmienka WHERE
    public int delete(String tabulka, String podmienka, Object[] parametre) throws SQLException {
        String sqlDotaz = "DELETE FROM " + tabulka + " WHERE " + podmienka;
        return vykonajDotaz(sqlDotaz, parametre);
    }
    // Metóda na vloženie záznamu do tabuľky
    public int insert(String tabulka, String[] stlpce, Object[] hodnoty) throws SQLException {
        if (stlpce.length != hodnoty.length) {
            throw new IllegalArgumentException("Počet stĺpcov a hodnôt sa musí zhodovať!");
        }
        // Generovanie SQL dotazu
        StringBuilder sqlDotaz = new StringBuilder();
        sqlDotaz.append("INSERT INTO ").append(tabulka).append(" (");

        // Pridanie názvov stĺpcov
        for (String stlpec : stlpce) {
            sqlDotaz.append(stlpec).append(", ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2); // odstráni poslednú čiarku a medzeru
        sqlDotaz.append(") VALUES (");

        // Pridanie otáznikov pre PreparedStatement kvôli hodnotám
        for (int i = 0; i < hodnoty.length; i++) {
            sqlDotaz.append("?, ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2);
        sqlDotaz.append(")");

        // Logovanie generovaného SQL dotazu
        System.out.println("Generovaný SQL dotaz: " + sqlDotaz);
        return vykonajDotaz(sqlDotaz.toString(), hodnoty);
    }
    // Metóda na aktualizáciu záznamu v tabuľke
    public int update(String tabulka, String[] stlpce, String podmienka, Object[] hodnoty) throws SQLException {
        if (stlpce.length + 1 != hodnoty.length) {
            throw new IllegalArgumentException("Počet stĺpcov a hodnôt sa musí zhodovať!");
        }
        // Generovanie SQL dotazu
        StringBuilder sqlDotaz = new StringBuilder();
        sqlDotaz.append("UPDATE ").append(tabulka).append(" SET ");

        // Pridanie stĺpcov na aktualizáciu
        for (String stlpec : stlpce) {
            sqlDotaz.append(stlpec).append(" = ?, ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2);
        sqlDotaz.append(" WHERE ").append(podmienka);

        // Logovanie generovaného SQL dotazu
        System.out.println("Generovaný SQL dotaz: " + sqlDotaz);
        return vykonajDotaz(sqlDotaz.toString(), hodnoty);
    }
    // Metóda na výber záznamov z tabuľky
    public ResultSet select(String tabulka, String[] stlpce, String podmienka, Object[] parametre) throws SQLException {
        StringBuilder sqlDotaz = new StringBuilder();
       sqlDotaz.append("SELECT ");

        // Pridanie stĺpcov na výber a ošetrenie
        if (stlpce == null || stlpce.length == 0) {
            sqlDotaz.append("*");
        } else {
            for (String stlpec : stlpce) {
                sqlDotaz.append(stlpec).append(", ");
            }
            sqlDotaz.setLength(sqlDotaz.length() - 2);
        }
        sqlDotaz.append(" FROM ").append(tabulka);

        // Pridanie podmienky WHERE, ak je zadaná
        if (podmienka != null && !podmienka.isEmpty()) {
            sqlDotaz.append(" WHERE ").append(podmienka);
        }
        // Logovanie generovaného SQL dotazu
        System.out.println("Generovaný SQL dotaz: " + sqlDotaz);

        // Vytvorenie PreparedStatement a nastavenie parametrov
        PreparedStatement pripravenyDotaz = connection.prepareStatement(sqlDotaz.toString());
        if (parametre != null) {
            for (int i = 0; i < parametre.length; i++) {
                pripravenyDotaz.setObject(i + 1, parametre[i]);
            }
        }
        return pripravenyDotaz.executeQuery();
    }
    // Metóda na spočítanie záznamov v tabuľke
    public int pocitadlo(String tabulka) throws SQLException {
        String sqlDotaz = "SELECT COUNT(*) AS pocet FROM " + tabulka;
        try (PreparedStatement pripravenyDotaz = connection.prepareStatement(sqlDotaz);
             ResultSet resultSet = pripravenyDotaz.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("pocet");
            } else {
                return 0;
            }
        }
    }
    // Metóda na aktualizáciu údajov klienta v databáze
    public int updateKlienta(Klient klient) throws SQLException {

        KlientDao builder = new KlientDao();  // ← doplnené!

        String[] stlpce = {"krstne_meno", "priezvisko", "datum_narodenia", "telefonne_cislo", "email", "adresa"};

        builder.update("klienti")
                .set(stlpce)
                .where("id = ?");

        String sqlDotaz = builder.getDotaz();

        Object[] hodnoty = {
                klient.getKrstneMeno(),
                klient.getPriezvisko(),
                klient.getDatumNarodenia(),
                klient.getTelefonneCislo(),
                klient.getEmail(),
                klient.getAdresa(),
                klient.getId()                 // posledný parameter pre WHERE id = ?
        };

        return vykonajDotaz(sqlDotaz, hodnoty);
    }
    // Metóda na vymazanie klienta z databázy
    public int vymazatKlienta(Klient klient) throws SQLException {
        KlientDao builder = new KlientDao();

        builder.delete("klienti")
                .where("id = ?");

        String sqlDotaz = builder.getDotaz();

        Object[] hodnoty = {
                klient.getId()
        };
        return vykonajDotaz(sqlDotaz, hodnoty);
    }
    // Metóda na načítanie všetkých klientov z databázy
    public List<Klient> nacitajVsetkychKlientov() throws SQLException{
        List<Klient> klienti = new ArrayList<>();

        String sqlDotaz = "SELECT * FROM klienti";
        ResultSet resultSet = select("klienti", null, null, null);

        try {
            while (resultSet.next()) {
                Klient klient = new Klient(
                        resultSet.getString("krstne_meno"),
                        resultSet.getString("priezvisko"),
                        resultSet.getString("email"),
                        resultSet.getString("telefonne_cislo"),
                        resultSet.getDate("datum_narodenia").toLocalDate()

                );
                klient.setId(resultSet.getInt("id"));
                klient.setAdresa(resultSet.getString("adresa"));
                klient.setDatumRegistracie(resultSet.getDate("datum_registracie").toLocalDate());
                klienti.add(klient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return klienti;
    }
}


