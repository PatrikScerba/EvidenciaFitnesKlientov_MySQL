package sk.patrikscerba.ui;

import sk.patrikscerba.db.KlientDaoImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// Trieda ZoznamKlientov zobrazuje okno so zoznamom klientov v tabuľke
public class ZoznamKlientov extends JFrame {

    private final JTable tabulka;

    // Konštruktor triedy ZoznamKlientov-inicializácia UI a načítanie klientov
    public ZoznamKlientov() throws SQLException {
        setTitle("Zoznam Klientov");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabulka = new JTable();
        add(new JScrollPane(tabulka));
        nacitajKlientov();  // jednoduché volanie
    }
    // Metóda načítava klientov z databázy a zobrazuje ich v tabuľke
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
        model.addColumn("Krstné meno");
        model.addColumn("Priezvisko");
        model.addColumn("E-mail");
        model.addColumn("Telefónne číslo");
        model.addColumn("Dátum narodenia");
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Zarovnanie doľava
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);


        tabulka.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Poradové číslo
        tabulka.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // ID
        tabulka.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Dátum narodenia
        tabulka.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Dátum registrácie

        // Meno, Priezvisko, Email, Telefón
        tabulka.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Krstné meno
        tabulka.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Priezvisko
        tabulka.getColumnModel().getColumn(4).setCellRenderer(leftRenderer); // Email
        tabulka.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Telefón
        }
    }




