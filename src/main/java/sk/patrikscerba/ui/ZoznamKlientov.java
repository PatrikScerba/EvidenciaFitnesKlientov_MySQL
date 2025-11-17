package sk.patrikscerba.ui;

import sk.patrikscerba.db.KlientDaoImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// Trieda ZoznamKlientov zobrazuje okno so zoznamom klientov v tabuľke
public class ZoznamKlientov extends JFrame {

    private final JTable tabulka;

    // Konštruktor triedy ZoznamKlientov
    public ZoznamKlientov() throws SQLException {
        setTitle("Zoznam Klientov");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabulka = new JTable();
        add(new JScrollPane(tabulka));
        nacitajKlientov();  // jednoduché volanie
    }
    // Metóda na načítanie klientov z databázy a zobrazenie v tabuľke
    public void nacitajKlientov() throws SQLException {

        KlientDaoImpl klientDao = new KlientDaoImpl();

        // Získanie všetkých klientov z databázy
        ResultSet resultSet = klientDao.select(
                "klienti",
                null,   // všetky stĺpce
                null,   // bez WHERE
                null    // bez parametrov
        );
        // Vytvorenie modelu tabuľky
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Poradové číslo");
        model.addColumn("ID");
        model.addColumn("Krstné Meno");
        model.addColumn("Priezvisko");
        model.addColumn("E-mail");
        model.addColumn("Telefónne Číslo");
        model.addColumn("Dátum Narodenia");
        model.addColumn("Dátum registrácie");

        // Naplnenie modelu tabuľky údajmi z ResultSet + poradie klientov v GUI zozname
        int poradie = 1;

        while (resultSet.next()) {
            Date datumNarodeniaSQL = resultSet.getDate("datum_narodenia");
            int vek = 0;

            if (datumNarodeniaSQL != null) {
                vek = LocalDate.now().getYear() - datumNarodeniaSQL.toLocalDate().getYear();
            }
            model.addRow(new Object[]{
                    poradie,
                    resultSet.getInt("id"),
                    resultSet.getString("krstne_meno"),
                    resultSet.getString("priezvisko"),
                    resultSet.getString("email"),
                    resultSet.getString("telefonne_cislo"),
                    resultSet.getDate("datum_narodenia"),
                    resultSet.getDate("datum_registracie")
            });
            poradie++;
        }
            tabulka.setModel(model);
        }
    }




