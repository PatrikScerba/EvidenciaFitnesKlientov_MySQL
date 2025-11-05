package sk.patrikscerba.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabazaPripojenie {

    private static final String URL = "jdbc:mysql://localhost:3306/evidencia_klientov";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Vráti pripojenie k databáze
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
