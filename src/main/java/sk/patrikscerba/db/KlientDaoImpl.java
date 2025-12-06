package sk.patrikscerba.db;

import sk.patrikscerba.model.Klient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Trieda poskytuje základné CRUD operácie pre tabuľku klienti (JDBC + MySQL)
public class KlientDaoImpl {

    // Pripojenie k databáze
    private Connection connection;

    // Získa nové pripojenie k databáze cez DatabazaPripojenie
    public KlientDaoImpl() throws SQLException {
        this.connection = DatabazaPripojenie.getConnection();
    }

    // Spustí SQL dotaz s parametrami (INSERT/UPDATE/DELETE)
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

    // Vymazanie záznamu z tabuľky podľa podmienky WHERE
    public int delete(String tabulka, String podmienka, Object[] parametre) throws SQLException {
        String sqlDotaz = "DELETE FROM " + tabulka + " WHERE " + podmienka;
        return vykonajDotaz(sqlDotaz, parametre);
    }

    // INSERT nového klienta do tabuľky
    public int insert(String tabulka, String[] stlpce, Object[] hodnoty) throws SQLException {

        if (stlpce.length != hodnoty.length) {
            throw new IllegalArgumentException("Počet stĺpcov a hodnôt sa musí zhodovať!");
        }

        StringBuilder sqlDotaz = new StringBuilder();
        sqlDotaz.append("INSERT INTO ").append(tabulka).append(" (");

        // Názvy stĺpcov
        for (String stlpec : stlpce) {
            sqlDotaz.append(stlpec).append(", ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2);
        sqlDotaz.append(") VALUES (");

        // Otázniky pre PreparedStatement
        for (int i = 0; i < hodnoty.length; i++) {
            sqlDotaz.append("?, ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2);
        sqlDotaz.append(")");

        return vykonajDotaz(sqlDotaz.toString(), hodnoty);
    }

    // Aktualizácia záznamu v tabuľke
    public int update(String tabulka, String[] stlpce, String podmienka, Object[] hodnoty) throws SQLException {

        if (stlpce.length + 1 != hodnoty.length) {
            throw new IllegalArgumentException("Počet stĺpcov a hodnôt sa musí zhodovať!");
        }

        StringBuilder sqlDotaz = new StringBuilder();
        sqlDotaz.append("UPDATE ").append(tabulka).append(" SET ");

        // Stĺpce na aktualizáciu
        for (String stlpec : stlpce) {
            sqlDotaz.append(stlpec).append(" = ?, ");
        }
        sqlDotaz.setLength(sqlDotaz.length() - 2);
        sqlDotaz.append(" WHERE ").append(podmienka);

        return vykonajDotaz(sqlDotaz.toString(), hodnoty);
    }

    // SELECT dotaz – vráti ResultSet
    public ResultSet select(String tabulka, String[] stlpce, String podmienka, Object[] parametre) throws SQLException {

        StringBuilder sqlDotaz = new StringBuilder();
        sqlDotaz.append("SELECT ");

        // Výber všetkých stĺpcov alebo konkrétnych
        if (stlpce == null || stlpce.length == 0) {
            sqlDotaz.append("*");
        } else {
            for (String stlpec : stlpce) {
                sqlDotaz.append(stlpec).append(", ");
            }
            sqlDotaz.setLength(sqlDotaz.length() - 2);
        }

        sqlDotaz.append(" FROM ").append(tabulka);

        if (podmienka != null && !podmienka.isEmpty()) {
            sqlDotaz.append(" WHERE ").append(podmienka);
        }

        PreparedStatement pripravenyDotaz = connection.prepareStatement(sqlDotaz.toString());

        if (parametre != null) {
            for (int i = 0; i < parametre.length; i++) {
                pripravenyDotaz.setObject(i + 1, parametre[i]);
            }
        }

        return pripravenyDotaz.executeQuery();
    }

    // Spočíta počet záznamov v tabuľke
    public int pocitadlo(String tabulka) throws SQLException {
        String sqlDotaz = "SELECT COUNT(*) AS pocet FROM " + tabulka;

        try (PreparedStatement pripravenyDotaz = connection.prepareStatement(sqlDotaz);
             ResultSet resultSet = pripravenyDotaz.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("pocet");
            }
            return 0;
        }
    }

    // Aktualizuje údaje klienta v databáze
    public int updateKlienta(Klient klient) throws SQLException {

        KlientDao builder = new KlientDao();

        String[] stlpce = {"krstne_meno", "priezvisko", "datum_narodenia",
                "telefonne_cislo", "email", "adresa"};

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
                klient.getId()
        };

        return vykonajDotaz(sqlDotaz, hodnoty);
    }

    // Vymazanie klienta podľa ID
    public int vymazatKlienta(Klient klient) throws SQLException {
        KlientDao builder = new KlientDao();

        builder.delete("klienti")
                .where("id = ?");

        String sqlDotaz = builder.getDotaz();

        Object[] hodnoty = { klient.getId() };

        return vykonajDotaz(sqlDotaz, hodnoty);
    }

    // Načíta všetkých klientov z databázy a vráti ich ako zoznam
    public List<Klient> nacitajVsetkychKlientov() throws SQLException {

        List<Klient> klienti = new ArrayList<>();
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
