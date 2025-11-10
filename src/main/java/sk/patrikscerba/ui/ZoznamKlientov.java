package sk.patrikscerba.ui;

import sk.patrikscerba.db.KlientDaoImpl;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZoznamKlientov extends JFrame {

    public ZoznamKlientov() throws SQLException {
        setTitle("Zoznam Klientov");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        KlientDaoImpl klientDao = new KlientDaoImpl();
        ResultSet resultSet = klientDao.select("klienti", null, null, null);
    }


}
